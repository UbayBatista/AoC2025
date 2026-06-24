# Día 05: Cafeteria (Parte A)

## 1. Descripción
El reto de la Parte A exige procesar un volcado de base de datos procedente del sistema de inventario de la cafetería de los elfos. El archivo se divide en dos secciones separadas por una línea en blanco: un listado de rangos numéricos inclusivos que definen qué identificadores corresponden a ingredientes frescos, y un listado de identificadores de ingredientes disponibles. El objetivo algorítmico es determinar cuántos de los identificadores disponibles se encuentran dentro de al menos un rango de frescura. Debido a la magnitud de los identificadores (valores de 64 bits), el dominio debe modelarse utilizando tipos numéricos largos (`long`).

## 2. Metodología
Se aplicó un enfoque estricto de **Test-Driven Development (TDD)** orientado al aislamiento de responsabilidades. Se diseñó una suite de pruebas granulares que verifican independientemente la inclusión geométrica 1D, el parseo del formato de texto plano y el algoritmo central de conteo. La implementación final delega el control de flujo a las estructuras funcionales de Java, garantizando la inmutabilidad de los datos transferidos entre capas.

## 3. Fundamentos y Principios
* **CAMA**: Se garantizó una arquitectura de **Alta Cohesión y Bajo Acoplamiento**. El servicio de dominio (`FreshIngredientCounter`) orquesta la regla de negocio desconociendo por completo los detalles de infraestructura, como la lectura de archivos o el particionado de cadenas, responsabilidades que recaen exclusivamente en `InventoryParser`.
* **SOLID**:
    * **SRP (Principio de Responsabilidad Única)**: Cada clase tiene un único motivo para cambiar. `IngredientRange` encapsula exclusivamente la definición de fronteras numéricas, `InventoryData` actúa como contenedor DTO, e `InventoryParser` centraliza la traducción de datos crudos a entidades del sistema.
* **Clean Code**:
    * **KISS y YAGNI**: No se implementaron algoritmos complejos de normalización o fusión de rangos superpuestos al no ser requeridos estrictamente por el dominio del problema. La solución más simple, basada en la evaluación booleana, resuelve el problema con eficacia.
    * **Good Naming**: Se utilizaron identificadores descriptivos que reflejan la abstracción adecuada (`countFresh`, `isFresh`, `contains`), suprimiendo la necesidad de comentarios explicativos.
* **Paradigma Funcional**:
    * **Inmutabilidad**: Las entidades portadoras de datos se modelaron utilizando `record` y sus colecciones internas se sellaron con `List.copyOf`, garantizando objetos inmutables libres de efectos secundarios.
    * **Streams y Method References**: Las iteraciones imperativas fueron reemplazadas por la API de `Streams`. Se utilizaron operaciones intermedias (`filter`) y terminales (`count`, `anyMatch`) junto con *Method References* para lograr una semántica altamente declarativa y concisa.