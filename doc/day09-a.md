# Día 09: Movie Theater (Parte A)

## 1. Descripción
El reto plantea calcular el área máxima de un rectángulo que pueda formarse utilizando dos baldosas rojas —representadas en una cuadrícula cartesiana bidimensional— como sus esquinas opuestas. Dado que las baldosas tienen una dimensión de $1 \times 1$, la fórmula matemática para calcular el área generada por un par de coordenadas $(x_1, y_1)$ y $(x_2, y_2)$ incluye ambas posiciones:
$Area = (|x_2 - x_1| + 1) \times (|y_2 - y_1| + 1)$.
El objetivo es procesar un conjunto de baldosas dadas, generar todas las combinaciones posibles (producto cartesiano) y determinar el área máxima resultante.

## 2. Metodología
Se mantuvo un ciclo estricto **TDD** (Red/Green/Refactor). Se diseñó una suite de pruebas en el paquete `software.aoc.day09.a` validando dos responsabilidades separadas: la correcta instanciación y cálculo geométrico por parte de la entidad `Tile`, y la correcta orquestación combinatoria por parte del servicio `MovieTheater`. Se garantizó el comportamiento esperado frente a los casos de prueba provistos antes de desarrollar la lógica de producción.

## 3. Fundamentos y Principios
* **CAMA y SOLID**:
    * **Principio de Responsabilidad Única (SRP) e Inmutabilidad**: La entidad `Tile` se modeló como un `record`, garantizando la inmutabilidad inherente a los datos espaciales. Esta clase es la única responsable de parsear su representación en texto y calcular su área respecto a otra baldosa.
    * **Tell, Don't Ask**: En lugar de extraer las coordenadas $(x, y)$ de las baldosas para calcular el área en un servicio externo, se delegó el cálculo directamente a la entidad `Tile` (`calculateAreaWith`), respetando la encapsulación geométrica.
* **Clean Code**:
    * **SLAP (Single Level of Abstraction Principle)**: En la clase `MovieTheater`, la orquestación del producto cartesiano se estructuró delegando la iteración secundaria al micro-método `calculateAreasWithAllTiles`. Esto permite que el método principal `calculateLargestRectangleArea` se lea fluidamente sin anidamientos opacos.
    * **KISS y YAGNI**: Se priorizó una solución funcional orientada a la máxima legibilidad sobre optimizaciones algorítmicas prematuras. Aunque la exclusión de pares redundantes (ej. $A \times B$ vs $B \times A$) es viable manteniendo el paradigma funcional mediante flujos declarativos basados en índices (`IntStream.range`), su implementación habría incrementado la opacidad estructural del *pipeline*, sacrificando la lectura fluida de las reglas de negocio a cambio de una optimización de rendimiento actualmente no requerida.
    * **Good Naming**: En el nivel de abstracción del parseo de cadenas, se utilizó el término concreto `coordinates` en lugar de nombres genéricos, documentando claramente el contenido del arreglo antes de su conversión numérica.
* **Paradigma Funcional**:
    * **Mónadas y Streams**: La resolución de las áreas se implementó sin variables de estado, utilizando `flatMapToLong` y `mapToLong`. La operación terminal `max()` devuelve una mónada `OptionalLong`, la cual se resuelve defensivamente mediante `orElse(0L)`, previniendo en tiempo de compilación cualquier posible `NullPointerException`.