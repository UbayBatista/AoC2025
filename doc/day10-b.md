# Día 10: Factory (Parte B)

## 1. Descripción
El reto de la Parte B introduce un cambio fundamental en el dominio del problema: el objetivo ya no es conmutar estados binarios de luces, sino alcanzar un nivel exacto de voltaje (Joltage) en una serie de contadores incrementales que parten de cero. Matemáticamente, el problema evoluciona de un sistema en el cuerpo finito $GF(2)$ a un modelo de Programación Lineal Entera (ILP) sobre los números Reales ($\mathbb{R}$), restringido a soluciones enteras no negativas ($\mathbb{Z}^+$). El objetivo central de optimización consiste en hallar el vector del espacio nulo que minimice la suma total de pulsaciones de botones (variables).

## 2. Metodología
Para satisfacer los requisitos sin comprometer la solución previa, se ha diseñado un nuevo contexto acotado (*Bounded Context*) estructurado en 7 clases altamente cohesionadas:
* **Factory, Machine y Button**: Modelo de dominio inmutable (`records`) centrado exclusivamente en los objetivos de voltaje y contadores afectados. Se emplea el principio *Tell, Don't Ask* para que la clase `Factory` orqueste el cálculo total delegando en el optimizador.
* **MachineParser**: Analizador léxico basado en expresiones regulares (Regex) y `Streams` que extrae únicamente los datos pertinentes, ignorando por diseño el estado de las luces iniciales.
* **LinearSystem y JoltageOptimizer**: El motor algebraico. `LinearSystem` es una estructura inmutable que representa la matriz y permite el cálculo de su Forma Escalonada Reducida por Filas (RREF) mediante recursividad. `JoltageOptimizer` implementa un algoritmo de Búsqueda en Profundidad (DFS) sobre el espacio nulo del sistema, evaluando las combinaciones posibles para hallar el mínimo global.

## 3. Fundamentos y Principios
* **CAMA y SOLID**:
    * **Principio Abierto/Cerrado (OCP)**: Se ha encapsulado esta solución en un nuevo paquete (`software.aoc.day10.b`), manteniendo el código de la Parte A completamente cerrado a modificaciones y protegiendo el sistema de regresiones.
    * **Responsabilidad Única (SRP)**: Existe una separación estricta entre la estructura de datos matricial (`LinearSystem`) y el algoritmo matemático de optimización (`JoltageOptimizer`).
    * **Inmutabilidad Profunda**: La clase `LinearSystem` garantiza cero efectos secundarios. Las operaciones matemáticas (`swapRows`, `normalizeRow`, `eliminateColumn`) generan sistemáticamente nuevas instancias de la matriz, posibilitando el *method chaining*.
* **Clean Code**:
    * **Intention-Revealing Names y SLAP**: Se han aplicado nombres de métodos que revelan claramente su intención matemática (ej. `findMinimumPresses`). El flujo del optimizador está fragmentado en micro-métodos, manteniendo un Único Nivel de Abstracción (SLAP).
    * **Erradicación de "Long Parameter List"**: Se ha utilizado un `record` interno (`MathContext`) dentro de `JoltageOptimizer` para agrupar las variables contextuales (columnas libres, rangos, etc.), aumentando la cohesión y limpiando las firmas de los métodos recursivos.
* **Paradigma Funcional y Algoritmia**:
    * **Recursión y Streams**: Todos los bucles imperativos (`for`, `while`) han sido sustituidos por la API de `Streams` (`IntStream`, `Stream.concat`) y llamadas recursivas puras, permitiendo un flujo de ejecución cien por cien declarativo.
    * **Mónadas**: El uso intensivo de `Optional<Integer>` en el proceso de validación del optimizador erradica las ramificaciones condicionales complejas, permitiendo la combinación funcional de resultados (`reduce(JoltageOptimizer::combineOptionals)`).