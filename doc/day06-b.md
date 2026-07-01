# Día 06: Trash Compactor (Parte B)

## 1. Descripción
La Parte B del reto introduce una modificación semántica completa en la lectura de los datos de la hoja de ejercicios (input). La disposición matricial se mantiene, pero la matemática de los cefalópodos exige que los operandos sean procesados en columnas verticales, leyéndose de derecha a izquierda dentro de cada bloque, y de arriba hacia abajo para conformar las cifras individuales de cada operando.

## 2. Metodología
Se mantuvo el ciclo **TDD (Test-Driven Development)**, rediseñando exclusivamente las pruebas de integración y de *parsing* (`WorksheetParserTest` y `WorksheetEvaluatorTest`) para validar la nueva lógica de extracción vertical. Debido al diseño modular previo, las pruebas unitarias del dominio central (`ProblemTest`) no requirieron modificación alguna, demostrando la alta estabilidad estructural del sistema.

## 3. Tests
Las pruebas garantizan la transición a la lectura vertical sin romper la estructura de las entidades:
* **`ProblemTest`**: Permanece inalterado, demostrando que la lógica matemática del dominio es independiente de la disposición espacial de los datos.
* **`WorksheetParserTest`**: Actualizado para validar que un bloque extraiga operandos concatenando los caracteres verticalmente (top-down) y procesando las columnas en orden inverso (right-to-left).
* **`WorksheetEvaluatorTest`**: Prueba de integración que asegura que el sumatorio final coincide con el nuevo resultado esperado de aplicar la lectura matricial ortogonal.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
    * **Principio Abierto/Cerrado (OCP)**: El diseño arquitectónico planteado en la Parte A demostró su solvencia. Las entidades de dominio (`Problem`), el patrón Strategy (`Operator`) y el orquestador (`WorksheetEvaluator`) permanecieron cerradas a la modificación. La nueva lógica se limitó exclusivamente a la refactorización del componente periférico `WorksheetParser` (abierto a la extensión).
    * **Principio DRY (Don't Repeat Yourself)**: Se reutilizó el micro-método `getSafeChar` para encapsular la validación de límites de índices bidimensionales, evitando la duplicación de bloques defensivos contra `IndexOutOfBoundsException`.
* **Clean Code**:
    * **Mónadas (Optional)**: Dado que en la lectura vertical pueden existir columnas intermedias vacías (espacios), se empleó `Optional<Long>` en el método `extractColumnNumber`. Esto previene el lanzamiento de `NumberFormatException`, permitiendo filtrar imperceptiblemente los opcionales vacíos en el *Pipeline* del *Stream*.
* **Paradigma Funcional y Algoritmia**:
    * **Pipelines Direccionales**: Se utilizó `IntStream.iterate(end - 1, col -> col >= start, col -> col - 1)` para implementar el recorrido espacial inverso (derecha a izquierda), manteniendo el flujo puramente declarativo y eliminando el uso de bucles imperativos decrementales.
    * **Acumulación de Cadenas**: La lectura top-down se resolvió mapeando espacialmente los caracteres y empleando el colector funcional `Collectors.joining()` para el ensamblaje de las cifras numéricas.

## 5. Diagrama UML
![Diagrama UML Dia 06 - Parte B](/images/day06.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Oculta la complejidad de la infraestructura de lectura del sistema de archivos e inyecta la matriz de texto cruda al evaluador.
* **WorksheetEvaluator**: Orquestador funcional (Servicio de Dominio). Al estar adherido al principio OCP, no sufre cambios; continúa conectando fluidamente el flujo desde el parser hasta la suma matemática terminal.
* **WorksheetParser**: Motor de análisis léxico bidimensional. Refactorizado para modificar el eje de lectura de los operandos. Implementa la extracción vertical top-down combinada con iteradores funcionales inversos (right-to-left), utilizando mónadas Optional para gobernar las disrupciones espaciales con seguridad.
* **Operator**: Enumeración funcional que implementa el patrón Strategy. Mantiene su pureza, ejecutando reducciones matemáticas sobre colecciones inmutables de enteros.
* **Problem**: Entidad principal inmutable del dominio. Sigue operando como contenedor seguro de operandos y operador, ignorando por completo que sus datos fueron recolectados mediante una topología espacial distinta.