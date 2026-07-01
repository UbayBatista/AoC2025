# Día 04: Printing Department (Parte B)

## 1. Descripción
El reto de la Parte B introduce una dinámica de evolución temporal sobre el almacén bidimensional. El objetivo es calcular el número total de rollos de papel retirados por las carretillas elevadoras a través de un proceso en cascada. Cuando un subconjunto de rollos cumple la condición de accesibilidad (menos de cuatro vecinos adyacentes), estos son eliminados de la cuadrícula. Esta modificación topológica genera un nuevo estado en el que otros rollos, previamente bloqueados, pueden volverse accesibles. El proceso se itera hasta alcanzar un estado estacionario donde ningún rollo adicional puede ser retirado.

## 2. Metodología
Se mantuvo el ciclo **TDD (Test-Driven Development)** bajo una estricta política de no regresión. En la fase RED, se conservó intacta la suite de pruebas de la Parte A para garantizar que la nueva lógica no corrompiera la regla base de accesibilidad. Se añadieron nuevas pruebas para simular el efecto cascada y aislar el caso base. Durante la fase GREEN, se adoptó directamente un enfoque puramente funcional basado en recursividad, lo que permitió satisfacer los requisitos de inmutabilidad desde el primer momento.

## 3. Tests
La suite `PaperGridTest` se amplió para validar la evolución del sistema a lo largo del tiempo, garantizando la prevención de regresiones:
* **Pruebas de No Regresión**: Se mantuvieron las aserciones de la Parte A (`should_count_accessible_rolls_from_example`, `should_identify_isolated_roll_as_accessible`, etc.) para asegurar que la invariante de accesibilidad base no fue alterada.
* **Prueba de Cascada (Integración)**: `should_calculate_total_removed_rolls_from_example` valida el complejo proceso iterativo de eliminación y reevaluación del sistema completo hasta alcanzar el estado estacionario.
* **Prueba de Caso Base**: `should_remove_single_accessible_roll_and_stop` aísla y verifica el comportamiento de la condición de salida de la recursividad (parada matemática cuando no hay más rollos accesibles).

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Bajo Acoplamiento Temporal (Inmutabilidad)**: El método `remove` proyecta una nueva instancia de `PaperGrid` en cada iteración del proceso en cascada, garantizando cero mutabilidad compartida.
  * **OCP (Principio Abierto/Cerrado)**: La clase `PaperGrid` se extendió con la nueva funcionalidad de eliminación recursiva y conteo acumulativo, pero permaneció completamente cerrada a la modificación respecto a la regla fundamental (`isAccessible`).
* **Clean Code**:
  * **Ocultación de Información (Information Hiding)**: Se empleó la sobrecarga de métodos (*Method Overloading*). El cliente (`Main`) interactúa con el contrato público limpio (`countTotalRemovedRolls()`), mientras que la clase oculta el método estático y privado que gobierna la complejidad de la recursión y el acumulador.
  * **DRY (Don't Repeat Yourself) y SLAP**: En la evaluación recursiva, el cálculo topológico se almacena en una variable local (`Set<Coordinate> accessible`) para evitar la reevaluación de los flujos de datos.
* **Paradigma Funcional y Algoritmia**:
  * **Recursión Pura sobre Iteración Imperativa**: Para gobernar la transición temporal entre estados sucesivos sin recurrir a estructuras de control imperativas mutables (bucles `while` o banderas booleanas), se implementó un diseño recursivo. El control de flujo recae en un operador ternario lógico puro.
  * **Proyección de Estados (Streams)**: La operación de borrado se ejecuta funcionalmente aplicando un `filter` inverso (`!toRemove.contains(roll)`) sobre el Stream de coordenadas original.

## 5. Diagrama UML
![Diagrama UML Dia 04 - Parte B](/images/day04-b.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Gestiona estrictamente la capa de infraestructura interactuando con el sistema de archivos, inyectando las líneas de texto bruto al dominio.
* **PaperGrid**: Entidad orquestadora del almacén topológico. Evoluciona para comportarse como un motor de estados inmutables. Emplea recursión funcional pura y proyección de colecciones (Streams) para simular el efecto cascada de eliminación temporal, instanciando nuevos planos espaciales en cada iteración hasta agotar las resoluciones lógicas.
* **Coordinate**: Entidad fundamental (Value Object) modelada como record inmutable. Encapsula su propia posición espacial y es autosuficiente para proyectar topológicamente las ocho coordenadas adyacentes a sí misma mediante la generación de un flujo (Stream).