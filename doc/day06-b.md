# Día 06: Trash Compactor (Parte B)

## 1. Descripción
La Parte B del reto introduce una modificación semántica completa en la lectura de los datos de la hoja de ejercicios (input). La disposición matricial se mantiene, pero la matemática de los cefalópodos exige que los operandos sean procesados en columnas verticales, leyéndose de derecha a izquierda dentro de cada bloque, y de arriba hacia abajo para conformar las cifras individuales de cada operando.

## 2. Metodología
Se mantuvo el ciclo **TDD**, diseñando pruebas de integración aisladas (`WorksheetParserTest` y `WorksheetEvaluatorTest`) para validar la nueva lógica de extracción vertical. Debido al diseño modular previo, las pruebas unitarias del dominio (`ProblemTest`) no requirieron modificaciones, demostrando la estabilidad estructural del sistema.

## 3. Fundamentos y Principios
* **Principio Abierto/Cerrado (OCP)**: El diseño arquitectónico planteado en la Parte A demostró su solvencia frente a la variabilidad de los requisitos. Las entidades de dominio (`Problem`), el patrón Strategy (`Operator`) y la orquestación (`WorksheetEvaluator`) permanecieron 100% inalteradas (cerradas a la modificación). La nueva lógica se limitó exclusivamente a una refactorización de la clase utilitaria `WorksheetParser` (abierta a la extensión).
* **Bajo Acoplamiento**: La separación estricta entre la representación léxica de los datos (la matriz de cadenas) y la lógica matemática de evaluación garantizó que el cambio de lectura horizontal a vertical no impactase en cascada al resto del sistema.
* **Principio DRY (Don't Repeat Yourself)**: Se extrajo la lógica de acceso seguro a caracteres bidimensionales hacia el micro-método `getSafeChar`, evitando la duplicación de validaciones de límites de índices (`IndexOutOfBounds`) a través de las distintas funciones de extracción.

## 4. Paradigma Funcional y Clean Code
* **Mónadas (Optional)**: Dado que en la lectura vertical pueden existir columnas intermedias compuestas enteramente por espacios, se empleó `Optional<Long>` en el método `extractColumnNumber`. Esto garantiza la seguridad frente a valores nulos y evita el lanzamiento de `NumberFormatException`, permitiendo filtrar imperceptiblemente los opcionales vacíos a través del `Stream`.
* **Pipelines Direccionales**: Se utilizó `IntStream.iterate(end - 1, col -> col >= start, col -> col - 1)` para implementar el recorrido inverso (de derecha a izquierda) requerido por el dominio, manteniendo el flujo puramente declarativo y sin recurrir a bucles `for` imperativos o a la mutación de listas intermedias.