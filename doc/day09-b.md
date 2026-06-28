# Día 09: Movie Theater (Parte B)

## 1. Descripción
El reto de la Parte B extiende la lógica de cálculo de áreas hacia la validación de espacios dentro de un polígono ortogonal. Dado un conjunto de vértices que definen el perímetro de una zona del cine, el objetivo es determinar el área máxima de un rectángulo ortogonal que esté contenido íntegramente dentro de dicho polígono, incluyendo sus puntos interiores y fronteras. La complejidad aumenta al requerir un motor geométrico capaz de detectar áreas cóncavas y evitar que el polígono atraviese el interior del rectángulo.

## 2. Metodología
Se ha implementado una solución basada en geometría computacional de alto rendimiento, evitando el escaneo de área (rasterización) por su ineficiencia en cuadrículas de gran escala. La implementación se estructura en tres componentes principales:
* **MovieTheater**: Orquestador de la lógica de negocio, encargado de realizar el producto cartesiano de las baldosas y filtrar los pares válidos mediante el motor geométrico.
* **TheaterPolygon**: Motor analítico que implementa el algoritmo de Ray-Casting para validación de pertenencia de puntos y validación de intersección de segmentos basada en AABB (Axis-Aligned Bounding Box o Caja Delimitadora Alineada a los Ejes) para asegurar la integridad del interior del rectángulo.
* **Tile**: Entidad de dominio (record) inmutable que encapsula las coordenadas y la lógica de cálculo de área.

## 3. Fundamentos y Principios
* **CAMA y SOLID**:
  * **Responsabilidad Única (SRP)**: La validación geométrica reside exclusivamente en TheaterPolygon, separando las preocupaciones matemáticas de las reglas de negocio de MovieTheater.
  * **Inmutabilidad**: El uso de records para Tile y la copia defensiva de listas (List.copyOf) garantizan la integridad de los datos espaciales.
* **Clean Code**:
  * **SLAP (Single Level of Abstraction Principle)**: La lógica matemática de detección de intersecciones se ha descompuesto en micro-métodos (isVerticalIntersection, isCoordinateStrictlyBetween, rangesOverlap), permitiendo que el flujo principal de validación (containsRectangle) se lea como una especificación de alto nivel.
  * **Good Naming**: Los nombres de variables y parámetros reflejan el rol geométrico de los elementos (ej. rectangleMinBound, segmentStart), logrando un código autodocumentado que elimina la ambigüedad matemática.
* **Algoritmia y Rendimiento**:
  * **Complejidad Algorítmica**: Se ha optimizado el cálculo de validación de O(W * H) a O(V), donde:
    - O(W * H) es la complejidad de la rasterización (donde W (Width) y H (Height) son las dimensiones del rectángulo, implicando un recorrido sobre cada punto unitario).
    - O(V) es la complejidad del motor analítico (donde V es el número de vértices del polígono), permitiendo una validación matemática directa independiente de las dimensiones del rectángulo.
  * **Paradigma Funcional**: Se ha empleado la API de Streams para realizar el filtrado combinatorio, utilizando flatMapToLong para procesar grandes volúmenes de datos sin sobrecarga de memoria mediante la manipulación de tipos primitivos.