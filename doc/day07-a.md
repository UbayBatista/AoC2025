# Día 07: Laboratories (Parte A)

## 1. Descripción
La Parte A de este reto exige simular la propagación de haces de taquiones a través de una matriz topológica bidimensional (el colector). La física del sistema estipula que los haces se desplazan verticalmente hacia abajo, se dividen en dos corrientes divergentes al encontrar un divisor (`^`), atraviesan sin interferencia el espacio vacío (`.`), y convergen matemáticamente si colisionan en la misma coordenada espacial durante una misma iteración. El objetivo radica en calcular el sumatorio total de eventos de división tras procesar la totalidad de las filas del colector.

## 2. Metodología
Se ha aplicado rigurosamente el ciclo de desarrollo **TDD (Test-Driven Development)**. La arquitectura orientada a pruebas permitió definir las aserciones basadas en comportamientos mediante JUnit y AssertJ. A través de una segregación estricta de responsabilidades, la física de la propagación del haz se probó de forma aislada (pruebas de unidad pura), garantizando la correcta localización de defectos (*Defect Localization*) previo al desarrollo del motor de simulación iterativo bidimensional.

## 3. Tests
Las aserciones se aislaron de manera granular para evaluar los fenómenos físicos de forma independiente:
* **Escaneo Inicial**: `should_locate_starting_position` valida el análisis de la primera fila para localizar el punto de inyección (`S`).
* **Física de Transmisión**: `should_transmit_freely_through_empty_space` garantiza la conservación de inercia a través del vacío.
* **Física de Bifurcación**: `should_split_beam_and_increment_counter_when_encountering_splitter` verifica la dispersión ortogonal y la acumulación correcta del evento.
* **Convergencia Matemática**: `should_merge_beams_landing_on_same_column` es una prueba estructural crítica; verifica que dos haces que convergen en el mismo punto exacto se fusionen de forma no destructiva (deduplicación).
* **Integración Topológica**: Comprueba que la simulación masiva arroje el total exacto estipulado por las reglas de negocio sobre un colector completo.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **SRP (Principio de Responsabilidad Única)**: Se garantiza la **Alta Cohesión** al segregar el sistema. `BeamPropagator` encapsula exclusivamente la lógica matemática de la física de propagación de un instante $T$. Por su parte, `TachyonManifold` asume la responsabilidad única de orquestar la topología global y suministrar los estratos espaciales.
  * **Composición sobre Herencia**: `TachyonManifold` incorpora el motor físico (`BeamPropagator`) mediante composición, previniendo el acoplamiento rígido estructural.
* **Patrones de Diseño**:
  * **Factory Method**: El `record` inmutable `PropagationStep` oculta la instanciación directa (`new`) exponiendo constructores estáticos con alta semántica de dominio (`empty()`, `freeTransmission()`, `splitEvent()`). Esto eleva el nivel de abstracción y aísla la lógica de negocio de los detalles de construcción de los conjuntos subyacentes.
* **Clean Code**:
  * **Micro-métodos y SLAP**: El componente físico delega la inspección de caracteres a `isSplitter`, lo que permite que la función principal se lea como una evaluación puramente lógica en un único nivel de abstracción.
* **Paradigma Funcional y Algoritmia**:
  * **Inmutabilidad Transaccional**: El estado del simulador se transporta entre iteraciones de fila mediante `PropagationStep`, actuando como un DTO matemático $100\%$ inmutable y libre de efectos secundarios.
  * **Abstracción Matemática sin Condicionales**: Se empleó la interfaz `Set` para almacenar las coordenadas espaciales activas. Esta decisión arquitectónica delega la compleja lógica de deduplicación geométrica a las propiedades innatas del conjunto, resolviendo colisiones automáticamente sin recurrir a estructuras de control imperativas.
  * **Iteración mediante Reducción Declarativa**: La transición temporal sobre las filas del espacio métrico se implementó inyectando una función acumuladora (`accumulate`) en el operador terminal `.reduce()` de la API de Streams.

## 5. Diagrama UML
![Diagrama UML Dia 07 - Parte A](/images/day07.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada estricto de la aplicación. Gestiona la I/O, instanciando el colector topológico e inyectándole las directivas de texto del modelo físico sin procesar.
* **TachyonManifold**: Entidad orquestadora del subdominio espacial (Colector). Encapsula el diseño bidimensional mediante listas inmutables y aplica recursión funcional (mediante Streams reduce) para simular el desplazamiento del tiempo a través del eje vertical de coordenadas, coordinando el motor físico subyacente.
* **BeamPropagator**: Servicio de dominio apátrida (stateless). Modela estrictamente el sistema de leyes físicas que interactúan a lo largo del eje horizontal en un ciclo de reloj determinado (fila), resolviendo colisiones y bifurcaciones vectoriales.
* **PropagationStep**: Objeto de transferencia de estados inmutable (Value Object tipo record). Alberga la fotocinta de eventos de propagación en un estado estático y expone métodos de fabricación puramente semánticos. Apela a teoría de conjuntos de la API Collections de Java para fusionar sus datos internamente evadiendo ramificaciones lógicas imperativas.