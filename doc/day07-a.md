# Día 07: Laboratories (Parte A)

## 1. Descripción
La Parte A de este reto exige simular la propagación de haces de taquiones a través de una matriz topológica bidimensional (el colector). La física del sistema estipula que los haces se desplazan verticalmente hacia abajo, se dividen en dos corrientes divergentes al encontrar un divisor (`^`), atraviesan sin interferencia el espacio vacío (`.`), y convergen matemáticamente si colisionan en la misma coordenada espacial durante una misma iteración. El objetivo radica en calcular el sumatorio total de eventos de división tras procesar la totalidad de las filas del colector.

## 2. Metodología
Se ha aplicado rigurosamente el ciclo de desarrollo **TDD** (Red/Green/Refactor). La arquitectura orientada a pruebas permitió definir las aserciones basadas en comportamientos mediante JUnit y AssertJ. A través de una segregación estricta de responsabilidades, la física de la propagación del haz se probó de forma aislada mediante pruebas unitarias puras, garantizando la correcta localización de defectos (*Defect Localization*) previo al desarrollo del motor de simulación iterativo.

## 3. Fundamentos y Principios
* **CAMA y SOLID**:
    * **SRP (Principio de Responsabilidad Única)**: Se garantiza la **Alta Cohesión** al segregar el sistema en dos componentes ortogonales. `BeamPropagator` encapsula exclusivamente la lógica matemática y física de la propagación de los haces. Por su parte, `TachyonManifold` asume la responsabilidad única de orquestar la topología y gestionar la simulación a lo largo de las filas.
    * **COI (Composición sobre Herencia)**: Se ha aplicado este principio estructurando `TachyonManifold` para que delegue el cálculo a una instancia de `BeamPropagator` mediante composición, evitando el acoplamiento rígido e innecesario que generaría la herencia.
* **Patrones de Diseño**:
    * **Factory Method**: Para preservar el encapsulamiento de la entidad de estado, el `record` `PropagationStep` oculta la instanciación directa (`new`) de cara a los clientes. En su lugar, expone métodos de fabricación estáticos con semántica de dominio explícita (`empty()`, `freeTransmission()`, `splitEvent()`). Esto eleva el nivel de abstracción y desacopla la lógica de negocio de los detalles de construcción de los objetos.
* **Clean Code**:
    * **Programación Declarativa**: Se prescindió de estructuras de control imperativas (como bucles `for` iterativos o sentencias `while`). El procesamiento de las colisiones en cada fila se resolvió mediante transformaciones `map`, mientras que la iteración global sobre la cuadrícula topológica se implementó aplicando la función de orden superior `reduce` de la API de `Streams`.
    * **Good Naming**: La selección meticulosa de identificadores y la extracción a micro-métodos (`evaluateBeam`, `isSplitter`) permiten que el sistema comunique la intención de diseño de manera unívoca, erradicando la necesidad de introducir comentarios explicativos.
* **Paradigma Funcional**:
    * **Inmutabilidad**: El estado transaccional acumulado se transporta entre iteraciones mediante `PropagationStep`, un `record` de Java que actúa como DTO cien por cien inmutable. Cada transición de estado genera una nueva instancia, lo que garantiza la ausencia total de efectos secundarios.
    * **Abstracciones Matemáticas**: Se empleó la interfaz `Set` para almacenar las coordenadas espaciales activas en el eje horizontal. Esta decisión arquitectónica resuelve la regla de convergencia de haces de forma inherente, explotando la propiedad matemática de unicidad del conjunto para fusionar coordenadas idénticas y eliminando lógica condicional superflua.