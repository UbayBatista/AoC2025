# Día 05: Cafeteria (Parte A)

## 1. Descripción
El reto de la Parte A exige procesar un volcado de base de datos procedente del sistema de inventario de la cafetería de los elfos. El archivo se divide en dos secciones separadas por una línea en blanco: un listado de rangos numéricos inclusivos que definen qué identificadores corresponden a ingredientes frescos, y un listado de identificadores de ingredientes disponibles. El objetivo algorítmico es determinar cuántos de los identificadores disponibles se encuentran dentro de al menos un rango de frescura. Debido a la magnitud de los identificadores (valores de 64 bits), el dominio debe modelarse utilizando tipos numéricos largos (`long`).

## 2. Metodología
Se aplicó un enfoque estricto de **Test-Driven Development (TDD)** orientado al aislamiento de responsabilidades. Se diseñó una suite de pruebas granulares que verifican independientemente la inclusión geométrica 1D, el parseo del formato de texto plano y el algoritmo central de conteo. La implementación final delega el control de flujo a las estructuras funcionales de Java, garantizando la inmutabilidad de los datos transferidos entre capas.

## 3. Tests
Las pruebas aseguran la localización de defectos distribuyendo las aserciones en tres capas ortogonales:
* **`IngredientRangeTest`**: Valida puramente la matemática geométrica de la entidad. Comprueba que las fronteras (caja blanca) sean inclusivas y rechaza correctamente los límites exteriores.
* **`InventoryParserTest`**: Aísla la infraestructura léxica. Verifica que el sistema secciona correctamente un volcado de texto mediante la identificación de líneas en blanco, mapeando de forma segura cada sección a su tipo correspondiente.
* **`FreshIngredientCounterTest`**: Prueba de orquestación (Integración de Dominio). Verifica que el motor principal iterará sobre todos los IDs disponibles y detectará si caen dentro de *cualquiera* de los rangos (superpuestos o no).

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Alta Cohesión y Bajo Acoplamiento**: El servicio de dominio (`FreshIngredientCounter`) orquesta la regla de negocio desconociendo por completo los detalles de infraestructura, como la lectura de archivos o el particionado de cadenas, responsabilidades que recaen exclusivamente en `InventoryParser`.
  * **SRP (Principio de Responsabilidad Única)**: Cada clase tiene un único motivo para cambiar. `IngredientRange` encapsula exclusivamente la definición de fronteras numéricas, `InventoryData` actúa como un DTO (*Data Transfer Object*), e `InventoryParser` centraliza la traducción de datos.
* **Clean Code**:
  * **KISS y YAGNI**: No se implementaron algoritmos complejos de normalización o fusión de rangos superpuestos al no ser requeridos estrictamente por el dominio de este problema. La solución más simple, basada en la evaluación booleana, resuelve el problema con eficacia.
  * **Good Naming**: Se utilizaron identificadores descriptivos que reflejan la abstracción adecuada (`countFresh`, `isFresh`, `contains`), suprimiendo la necesidad de comentarios explicativos.
* **Paradigma Funcional e Inmutabilidad**:
  * **Inmutabilidad Defensiva**: Las entidades portadoras de datos se modelaron utilizando `record` y sus colecciones internas se sellaron estáticamente con `List.copyOf`, garantizando objetos libres de efectos secundarios compartidos (*Shared Mutable State*).
  * **Streams Segmentados**: En lugar de bucles iterativos, se combinaron `limit` y `skip` sobre el *stream* de entrada para fragmentar los datos de manera declarativa. La agregación booleana se resolvió elegantemente mediante `anyMatch`.

## 5. Diagrama UML
![Diagrama UML Dia 05 - Parte A](/images/day05-a.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Gestiona la infraestructura de entrada/salida para la lectura de archivos y orquesta el flujo inyectando los datos al parser y al motor evaluador.
* **InventoryParser**: Clase utilitaria encargada del análisis léxico. Escanea el texto sin procesar, segmenta la información utilizando la estructura del documento (línea en blanco) y construye las entidades de dominio correspondientes a través de flujos declarativos (limit y skip).
* **InventoryData**: Objeto de Transferencia de Datos (DTO) modelado como record inmutable. Su única responsabilidad es transportar de manera segura las colecciones de rangos y los IDs disponibles desde el parser hasta el motor del dominio.
* **FreshIngredientCounter**: Motor algorítmico y servicio de dominio principal. Recibe una colección inmutable de rangos y cuenta declarativamente cuántos elementos de una lista dada cumplen la condición de frescura.
* **IngredientRange**: Entidad espacial matemática (Value Object inmutable). Conoce sus límites y asume la responsabilidad exclusiva de evaluar la inclusión geométrica 1D de un número dado en su interior (isFresh).