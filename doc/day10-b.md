# Día 10: Factory (Parte B)

## 1. Descripción
El reto de la Parte B introduce un cambio fundamental en el dominio del problema: el objetivo ya no es conmutar estados binarios de luces, sino alcanzar un nivel exacto de voltaje (Joltage) en una serie de contadores incrementales que parten de cero. Matemáticamente, el problema evoluciona de un sistema en el cuerpo finito $GF(2)$ a un modelo de Programación Lineal Entera (ILP) sobre los números Reales ($\mathbb{R}$), restringido a soluciones enteras no negativas ($\mathbb{Z}^+$). El objetivo central de optimización consiste en hallar el vector del espacio nulo que minimice la suma total de pulsaciones de botones (variables).

## 2. Metodología
Para satisfacer los requisitos sin comprometer la solución previa, se ha diseñado un nuevo contexto acotado (*Bounded Context*) estructurado en clases altamente cohesionadas. Guiado por **TDD**, el diseño orientó las pruebas a verificar la estabilidad numérica del algoritmo de Gauss-Jordan (uso de `double`) y la correcta exploración recursiva (Búsqueda en Profundidad - DFS) de las combinaciones posibles en el espacio nulo matricial.

## 3. Tests
Las pruebas garantizan la fiabilidad algorítmica y léxica frente a la evolución del dominio:
* **`MachineParserTest`**: Valida que la expresión regular extraiga correctamente los voltajes objetivo `{...}` e ignore deliberadamente la configuración binaria `[...]`, adaptándose a las nuevas reglas de negocio.
* **`JoltageOptimizerTest`**: Aísla el motor de optimización. Comprueba que la reducción de Gauss-Jordan y la subsecuente exploración del espacio nulo mediante combinatoria recursiva localicen el mínimo global exacto (solución entera).
* **`FactoryTest`**: Prueba de integración orquestada que corrobora la sumatoria final (`33`) al resolver de forma independiente todas las máquinas del archivo.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Principio Abierto/Cerrado (OCP)**: Se ha encapsulado esta solución en un nuevo paquete (`software.aoc.day10.b`), manteniendo el código de la Parte A completamente cerrado a modificaciones y protegiendo el sistema de regresiones.
  * **Responsabilidad Única (SRP)**: Existe una separación estricta entre la estructura de datos matricial (`LinearSystem`) y el algoritmo matemático de optimización ILP (`JoltageOptimizer`).
* **Clean Code**:
  * **Mitigación de "Long Parameter List"**: Se ha introducido un objeto de contexto (`MathContext`), modelado como un `record` privado, para agrupar variables interdependientes (columnas libres, rangos máximos, referencias a pivotes). Esto limpia drásticamente las firmas de los métodos recursivos.
  * **Inmutabilidad Profunda y Encadenamiento**: La clase `LinearSystem` garantiza cero efectos secundarios mediante clonación defensiva. Las operaciones algebraicas (`swapRows`, `normalizeRow`) devuelven siempre nuevas instancias, habilitando un *Method Chaining* declarativo y fluido (`reduceRecursive`).
* **Paradigma Funcional y Algoritmia**:
  * **Estabilidad Numérica (Pivoteo Parcial)**: Para operar con tipos `double` mitigando el error de redondeo, el método `findMaxAbsoluteRow` busca el elemento de mayor magnitud antes de cada eliminación.
  * **Mónadas para Control de Flujo**: Se empleó intensivamente `Optional<Integer>` en el optimizador (`validateAndCountPresses`). Esto permite filtrar elegantemente soluciones inválidas (fraccionarias o negativas) y combinar las ramificaciones funcionales a través de `reduce(JoltageOptimizer::combineOptionals)`, erradicando complejas sentencias condicionales.

## 5. Diagrama UML
![Diagrama UML Dia 10 - Parte B](/images/day10-b.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Gestiona la I/O, procesa las líneas crudas mediante el analizador léxico y delega el cálculo de la optimización global a la entidad factoría.
* **Factory**: Orquestador funcional del dominio. Encapsula las máquinas y coordina una tubería declarativa (Stream) que aplica el servicio optimizador sobre cada una para totalizar la cantidad de pulsaciones de botones.
* **MachineParser**: Analizador léxico utilitario. Utiliza expresiones regulares para extraer de forma tolerante a fallos la información requerida por el dominio B (los voltajes objetivo).
* **JoltageOptimizer**: Motor de Programación Lineal Entera (ILP). Aísla el algoritmo de Búsqueda en Profundidad (DFS) sobre el espacio nulo matricial. Emplea objetos de contexto internos y Mónadas de Java (Optional) para gobernar el flujo recursivo de evaluación de manera puramente funcional.
* **LinearSystem**: Entidad algebraica (Value Object). Modela un sistema de ecuaciones mediante una matriz inmutable de tipo double. Provee cálculos matemáticos estables mediante pivoteo parcial e implementa el método de Gauss-Jordan puramente funcional (Forma Escalonada Reducida por Filas).
* **Machine y Button**: Entidades DTO inmutables (records). Adaptadas estructuralmente a las nuevas necesidades semánticas, transportan los arreglos de índices afectados y los umbrales de voltajes esperados.