# Día 02: Gift Shop (Parte B)

## 1. Descripción
La Parte B introduce una evolución en la regla de negocio del dominio: el criterio de invalidez de un identificador de producto se expande. Mientras que originalmente se buscaba una repetición doble exacta, ahora un ID se considera inválido si está formado por un patrón numérico que se repite consecutivamente al menos dos veces (pudiendo ser tres, cuatro, cinco o más repeticiones, de forma que el patrón cubra la totalidad de la cadena). El objetivo se mantiene en calcular la sumatoria de todos los identificadores que cumplen esta nueva condición.

## 2. Metodología
Se continuó con el ciclo **TDD (Test-Driven Development)** expandiendo la suite de pruebas del dominio. Se introdujeron nuevas aserciones para validar secuencias con múltiples repeticiones (`111`, `11111111`, `2121212121`). Se preservaron rigurosamente las pruebas de frontera y de aislamiento (como la evaluación de un rango único) para garantizar la localización precisa de defectos (Defect Localization) ante posibles fallos en el mecanismo de particionado o sanitización.

## 3. Fundamentos y Principios
* **CAMA**: Se conservó la **alta cohesión** aislando el nuevo análisis combinatorio estrictamente dentro de la entidad `GiftId`. El **bajo acoplamiento** se evidencia al mantener intacta la firma del contrato de esta entidad con la clase coordinadora `InvalidGiftIdCalculator`.
* **SOLID**:
    * **SRP (Responsabilidad Única)**: Se separó conceptualmente la orquestación del flujo de datos y la evaluación morfológica del identificador.
    * **OCP (Abierto/Cerrado)**: La tubería de cálculo (`InvalidGiftIdCalculator`) y el particionado (`Range`) se mantuvieron cerrados para su modificación; la nueva funcionalidad se adaptó alterando exclusivamente la lógica interna de la entidad de dominio.
* **Clean Code**:
    * Se aplicó la técnica de encadenamiento de métodos (*method chaining*) en el particionado de la entrada para eliminar variables locales temporales, reduciendo la complejidad ciclomática y los estados intermedios en memoria.
    * El uso de **micro-métodos** en la clase `GiftId` segmentó la complejidad del análisis combinatorio (`isExactDivisor`, `isRepeatedPattern`).
* **Paradigma Funcional y Streams API**: Se resolvió el problema combinatorio de forma puramente declarativa mediante `IntStream.rangeClosed`, `filter` y `anyMatch`, sustituyendo la iteración imperativa clásica. Adicionalmente, se integró el método `String.repeat()` para evaluar la simetría de la cadena sin bucles de concatenación mutables.