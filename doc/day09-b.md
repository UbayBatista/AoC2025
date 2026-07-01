# Día 09: Movie Theater (Parte B)

## 1. Descripción
El reto de la Parte B extiende la lógica de cálculo de áreas hacia la validación de espacios dentro de un polígono ortogonal. Dado un conjunto de vértices que definen el perímetro de una zona del cine, el objetivo es determinar el área máxima de un rectángulo ortogonal que esté contenido íntegramente dentro de dicho polígono, incluyendo sus puntos interiores y fronteras. La complejidad aumenta al requerir un motor geométrico capaz de detectar áreas cóncavas y evitar que el polígono atraviese el interior del rectángulo.

## 2. Metodología
Se ha implementado una solución basada en geometría computacional de alto rendimiento, evitando el escaneo de área por fuerza bruta (rasterización) debido a su ineficiencia en cuadrículas de gran escala. Manteniendo el ciclo **TDD (Test-Driven Development)**, se desarrolló y aisló el motor geométrico verificando su precisión analítica antes de integrarlo en el flujo combinatorio del orquestador principal.

## 3. Tests
Las aserciones se aislaron para validar la precisión del motor analítico sin depender del orquestador:
* **`TheaterPolygonTest`**: Valida exhaustivamente el motor de colisiones espaciales. Comprueba casos positivos (`should_return_true_when_rectangle_is_fully_inside_polygon`) y detecta falsos positivos bloqueando rectángulos que cruzan las fronteras cóncavas del polígono (`should_return_false_when_rectangle_is_not_fully_inside_polygon`).
* **`MovieTheaterTest`**: Asegura la no regresión de la Parte A e incorpora la validación integral del cálculo de la máxima área válida (`calculateLargestValidRectangleArea`), confirmando el correcto filtrado del producto cartesiano a través del motor geométrico.
* **`TileTest`**: Permanece inalterado, demostrando la estabilidad de la entidad espacial base.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Responsabilidad Única (SRP)**: La validación geométrica compleja reside exclusivamente en `TheaterPolygon`, separando las preocupaciones matemáticas de las reglas de iteración y negocio que gobierna `MovieTheater`.
  * **Inmutabilidad**: El uso de copias defensivas (`List.copyOf`) al inicializar el polígono garantiza la integridad estructural de los vértices durante toda la ejecución.
* **Algoritmia Computacional y Rendimiento**:
  * **Complejidad Temporal Optimizada**: Se ha optimizado el cálculo de validación de $O(W \times H)$ a $O(V)$, donde $O(W \times H)$ es la complejidad de la rasterización (recorrido ineficiente sobre cada punto unitario del área) y $O(V)$ es la complejidad del motor analítico dependiente solo del número de vértices del polígono.
  * **Ray-Casting y AABB**: Se empleó el algoritmo de *Ray-Casting* (trazado de rayos) para la validación de pertenencia de puntos interiores, y validación de intersección de segmentos basada en AABB (*Axis-Aligned Bounding Box*) para asegurar que ningún muro atraviese el rectángulo.
* **Clean Code**:
  * **SLAP (Single Level of Abstraction Principle)**: La densidad matemática se mitigó mediante una descomposición extrema en micro-métodos (`isVerticalIntersection`, `isCoordinateStrictlyBetween`, `rangesOverlap`). Esto permite que el flujo de validación (`containsRectangle`) se lea como una especificación de alto nivel.
  * **Good Naming**: Se utilizaron identificadores explícitos (`rectangleMinBound`, `segmentStart`), logrando un código autodocumentado que elimina la ambigüedad algebraica.

## 5. Diagrama UML
![Diagrama UML Dia 09 - Parte B](images/day09-b.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Responsable de la infraestructura, encapsula la lectura del fichero de entrada y proyecta las líneas de texto en una lista de entidades de dominio (Tile) antes de pasarlas al orquestador.
* **MovieTheater**: Orquestador funcional del dominio. Delega la validación espacial al motor poligonal e itera declarativamente sobre las combinaciones de baldosas que superan el filtro geométrico, extrayendo el área máxima permitida.
* **TheaterPolygon**: Motor analítico y geométrico. Aísla toda la complejidad matemática de la validación espacial. Implementa algoritmos de alto rendimiento computacional (Ray-Casting y AABB) mediante una extensa red de micro-métodos puros orientados a garantizar el principio SLAP.
* **Tile**: Entidad matemática y geométrica (Value Object inmutable). Representa un punto discreto en el espacio bidimensional y provee los cálculos de área fundamentales para la resolución del problema.