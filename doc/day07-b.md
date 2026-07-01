# Día 07: Laboratories (Parte B)

## 1. Descripción
La Parte B del reto introduce un cambio de paradigma en las reglas físicas del dominio: se transiciona de una mecánica de taquiones clásica a una interpretación cuántica (multiverso). Bajo esta nueva premisa, al encontrar un divisor (`^`), el haz no se divide físicamente en el mismo espacio, sino que el universo se bifurca creando dos líneas temporales distintas. Consecuentemente, cuando dos o más trayectorias convergen en la misma coordenada espacial, no deben deduplicarse (fusionarse de forma destructiva), sino que las líneas temporales concurrentes deben sumarse aditivamente. El objetivo es calcular la cardinalidad total de líneas temporales activas al finalizar el recorrido topológico.

## 2. Metodología
Se mantuvo de forma estricta el ciclo **TDD (Test-Driven Development)**. La alteración de las leyes físicas exigió rediseñar los contratos de las pruebas unitarias para que verificasen mapas de frecuencias en lugar de conjuntos de coordenadas únicas. Esta aproximación permitió validar el comportamiento de bifurcación cuántica y la convergencia aditiva de forma aislada, garantizando la correcta transición de estados espaciales antes de ensamblar la simulación topológica completa.

## 3. Tests
Las aserciones se actualizaron para modelar la nueva naturaleza aditiva de las líneas temporales, asegurando la localización de defectos:
* **Física de Transmisión Cuántica**: `should_transmit_freely_through_empty_space_preserving_timelines` valida que la inercia a través del espacio mantenga intacto el peso (frecuencia) de la línea temporal.
* **Bifurcación del Multiverso**: `should_split_into_two_timelines_when_encountering_splitter` confirma que un divisor proyecta la misma cantidad de líneas temporales hacia la izquierda y hacia la derecha.
* **Convergencia Aditiva (Prueba Crítica)**: `should_accumulate_timelines_when_converging_on_same_column` verifica matemáticamente que los haces que colisionan en la misma coordenada sumen sus pesos (ej. $1 + 1 = 2$) en lugar de sobrescribirse.
* **Integración Topológica**: Comprueba que la simulación masiva acumule correctamente el total de multiversos activos (`40L`).

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Alta Cohesión y SRP**: El diseño reafirma la separación de responsabilidades. `TachyonManifold` continúa operando exclusivamente como el orquestador del espacio, mientras que `BeamPropagator` asume íntegramente la complejidad matemática de la ramificación. `PropagationStep` fue simplificado para actuar como un DTO inmutable puro, elevando su cohesión al desvincularse de lógicas de combinación.
* **Clean Code**:
  * **SLAP (Single Level of Abstraction Principle)**: La instanciación de los flujos se ha encapsulado en micro-métodos puros (`splitQuantumTimelines` y `preserveQuantumTimeline`). Esto garantiza que el método evaluador principal hable en términos de lógica de negocio (el *qué*), delegando los detalles de construcción de la API de Streams (el *cómo*) a funciones de bajo nivel.
  * **Good Naming**: Se aplicó una corrección léxica en la clase `Main`, renombrando la función de salida a `printTotalTimelines` para reflejar unívocamente la intención y el vocabulario del nuevo dominio, favoreciendo la legibilidad sin comentarios.
* **Paradigma Funcional y Algoritmia**:
  * **Mapeo de Frecuencias (Map)**: Se sustituyó la estructura `Set` por `Map<Integer, Long>`. La clave representa la coordenada espacial y el valor encapsula la cantidad de líneas temporales concurrentes. Esta decisión modela con precisión la matemática del multiverso.
  * **Streams y Acumulación Declarativa**: Para gestionar la convergencia cuántica sin lógicas condicionales ni bucles iterativos, se proyectaron las bifurcaciones mediante `flatMap`. Posteriormente, se utilizó `Collectors.toMap` inyectando la referencia `Long::sum`. Esto delega la consolidación de claves duplicadas a una resolución puramente funcional y aditiva.

## 5. Diagrama UML
![Diagrama UML Dia 07 - Parte A](images/day07.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada estricto de la aplicación. Gestiona la I/O, instanciando el colector topológico cuántico e inyectándole las directivas de texto del modelo físico sin procesar, refactorizado para reflejar la terminología de "líneas temporales".
* **TachyonManifold**: Entidad orquestadora del subdominio espacial. Encapsula el diseño bidimensional y aplica recursión funcional para simular el desplazamiento del tiempo, extrayendo al final la sumatoria total de los valores del mapa resultante.
* **BeamPropagator**: Servicio de dominio inmutable. Resuelve la física cuántica del sistema a través del eje horizontal. Emplea flatMap y colectores aditivos (Long::sum) para bifurcar universos y fusionar líneas temporales solapadas de manera matemáticamente declarativa, previniendo estructuras condicionales.
* **PropagationStep**: Objeto de transferencia de datos inmutable (Value Object). Refactorizado en su forma más pura como un record que encapsula el mapa de frecuencias (coordenada espacial frente al número de líneas temporales concurrentes) en un instante determinado.