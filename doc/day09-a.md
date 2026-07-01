# Día 09: Movie Theater (Parte A)

## 1. Descripción
El reto plantea calcular el área máxima de un rectángulo que pueda formarse utilizando dos baldosas rojas —representadas en una cuadrícula cartesiana bidimensional— como sus esquinas opuestas. Dado que las baldosas tienen una dimensión de $1 \times 1$, la fórmula matemática para calcular el área generada por un par de coordenadas $(x_1, y_1)$ y $(x_2, y_2)$ incluye ambas posiciones:
$Area = (|x_2 - x_1| + 1) \times (|y_2 - y_1| + 1)$.
El objetivo es procesar un conjunto de baldosas dadas, generar todas las combinaciones posibles (producto cartesiano) y determinar el área máxima resultante.

## 2. Metodología
Se mantuvo un ciclo estricto **TDD (Test-Driven Development)** (Red/Green/Refactor). Se diseñó una suite de pruebas validando dos responsabilidades separadas: la correcta instanciación y el cálculo geométrico por parte de la entidad `Tile`, y la correcta orquestación combinatoria por parte del servicio `MovieTheater`. Se garantizó el comportamiento esperado frente a los casos de prueba provistos antes de desarrollar la lógica algorítmica de producción.

## 3. Tests
Las aserciones se aislaron para evaluar la geometría matemática frente a la algoritmia combinatoria:
* **`TileTest`**: Asegura el comportamiento intrínseco de la entidad espacial. Verifica el análisis léxico bidimensional (`should_parse_coordinate_string_into_tile`) y la correcta aplicación de la fórmula geométrica. Además, valida la propiedad conmutativa matemática (`should_calculate_correct_area_regardless_of_order`), demostrando que el uso de valor absoluto (`Math.abs`) hace que el orden de evaluación de las esquinas sea irrelevante.
* **`MovieTheaterTest`**: Prueba de integración que verifica la orquestación del producto cartesiano. Valida que, ante una nube de puntos, el motor algorítmico explore todas las combinaciones y extraiga exactamente el área de la envolvente máxima.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Principio de Responsabilidad Única (SRP) e Inmutabilidad**: La entidad `Tile` se modeló como un `record`, garantizando la inmutabilidad inherente a los datos espaciales. Esta clase es la única responsable de interpretar su representación en texto y calcular su área.
  * **Tell, Don't Ask (Bajo Acoplamiento)**: En lugar de extraer las coordenadas $(x, y)$ de las baldosas para calcular el área en un servicio externo, el orquestador delega el cálculo directamente a la entidad `Tile` mediante la llamada `calculateAreaWith`, respetando la encapsulación geométrica.
* **Clean Code**:
  * **SLAP (Single Level of Abstraction Principle)**: En la clase `MovieTheater`, la generación del producto cartesiano se estructuró delegando la iteración del segundo bucle al micro-método `calculateAreasWithAllTiles`. Esto permite que el flujo principal `calculateLargestRectangleArea` se lea secuencialmente sin anidamientos opacos.
  * **KISS y YAGNI**: Se priorizó una solución funcional orientada a la máxima legibilidad sobre optimizaciones algorítmicas prematuras. Excluir las combinaciones redundantes habría incrementado la complejidad estructural del código; al no haber problemas de rendimiento, la solución actual es la más limpia.
* **Paradigma Funcional**:
  * **Mónadas y Streams Primitivos**: La resolución se implementó sin variables de estado, utilizando `flatMapToLong` y `mapToLong` para evitar el *Autoboxing Overhead* de objetos numéricos. La operación terminal `max()` devuelve un `OptionalLong`, resuelto defensivamente mediante `orElse(0L)` para prevenir excepciones.

## 5. Diagrama UML
![Diagrama UML Dia 09 - Parte A](/images/day09-a.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Responsable de la infraestructura, encapsula la lectura del fichero de entrada y proyecta las líneas de texto en una lista de entidades de dominio (Tile) antes de pasarlas al motor de evaluación.
* **MovieTheater**: Orquestador funcional del dominio. Encapsula una colección de baldosas y aplica combinatoria funcional mediante flujos declarativos (Streams) para computar el producto cartesiano de todas las baldosas, extrayendo el área rectangular máxima resultante.
* **Tile**: Entidad matemática y geométrica (Value Object inmutable). Representa un punto discreto en el espacio bidimensional. Es responsable de la inicialización de sus propias coordenadas desde cadenas de texto y encapsula la fórmula algebraica para calcular el área de un rectángulo formado respecto a otra baldosa, empleando valores absolutos para garantizar la conmutatividad.
