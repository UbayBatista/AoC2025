# Día 05: Cafeteria (Parte B)

## 1. Descripción
La Parte B del reto introduce una modificación semántica en la interpretación de los datos de inventario. A diferencia de la Parte A, la lista de identificadores disponibles es irrelevante; el objetivo es calcular la cardinalidad total del conjunto unión formado por una serie de rangos numéricos superpuestos. Dado que los intervalos pueden solaparse, el reto se transforma en un problema geométrico 1D de fusión de intervalos disjuntos para calcular la suma total de identificadores únicos frescos.

## 2. Metodología
Se empleó el ciclo **TDD (Test-Driven Development)** manteniendo el aislamiento total respecto a la Parte A mediante la creación de un nuevo *Bounded Context* (paquete `b`). Se implementó un algoritmo de ordenación y fusión de rangos que garantiza la transformación de solapamientos en un conjunto de intervalos disjuntos. La solución integra un paradigma puramente funcional, eliminando estructuras de control imperativas en favor de operaciones de acumulación declarativa.

## 3. Tests
Las pruebas se adaptaron para validar la nueva regla de negocio, garantizando el blindaje de la lógica geométrica:
* **`IngredientRangeTest`**: Se amplió para verificar la correcta resolución del método `capacity()`, asegurando que la matemática de conteo inclusivo (extremo superior - extremo inferior + 1) sea exacta.
* **`InventoryParserTest`**: Valida el comportamiento de cortocircuito en la lectura, confirmando que el analizador ignora deliberadamente la segunda mitad del archivo (los IDs irrelevantes) al encontrar la línea en blanco.
* **`TotalFreshIngredientCounterTest`**: Prueba de integración algorítmica. Verifica que un conjunto de rangos solapados y desordenados se fusione correctamente y que la suma de sus capacidades refleje la cardinalidad de la unión, sin duplicar elementos.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y Tell, Don't Ask**:
  * Se ha aplicado rigurosamente el principio *Tell, Don't Ask*. En lugar de exponer el estado interno de `IngredientRange` para que servicios externos calculen su longitud, el servicio solicita al objeto que realice sus propios cálculos mediante el método `capacity()`.
  * La entidad implementa `Comparable` para dotar al dominio de una heurística natural de ordenación matemática basada en el límite inferior.
* **SOLID**:
  * **SRP**: El diseño separa estrictamente la infraestructura de lectura parcial (`InventoryParser`) de la lógica matemática de fusión (`TotalFreshIngredientCounter`).
  * **OCP**: La nueva funcionalidad reside en un paquete independiente, evitando la modificación en los componentes de la Parte A y protegiendo el sistema contra regresiones.
* **Clean Code y Rendimiento**:
  * **Uso Eficiente de Estructuras**: Se utilizó `LinkedList` como acumulador dentro de la reducción funcional. Al comportarse como una cola doble (Deque), permite operaciones de acceso y borrado en el último elemento (`peekLast`, `removeLast`) con complejidad temporal constante $O(1)$.
* **Paradigma Funcional y Streams**:
  * **Evaluación de Cortocircuito (Short-circuiting)**: En el parser, se empleó `takeWhile(line -> !line.trim().isEmpty())` para consumir el flujo de texto solo hasta que la semántica del dominio lo requiere, evitando procesar datos inútiles.
  * **Mónadas para Control de Flujo**: En lugar de comprobaciones imperativas `if (last != null && overlaps)`, la lógica de fusión delega en `Optional.ofNullable(...).filter(...).ifPresentOrElse(...)`, garantizando seguridad contra nulos y expresividad declarativa.

## 5. Diagrama UML
![Diagrama UML Dia 05 - Parte A](images/day05-b.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Gestiona la infraestructura de entrada/salida y orquesta el flujo pasando las líneas crudas hacia el parser y derivando el resultado al contador total.
* **InventoryParser**: Clase utilitaria encargada del análisis léxico adaptado a la Parte B. Utiliza operaciones de cortocircuito (takeWhile) para procesar únicamente el segmento del archivo que declara los rangos, ignorando el resto del volcado por diseño.
* **TotalFreshIngredientCounter**: Servicio de dominio matemático estático. Encapsula el motor algorítmico para la ordenación y fusión de intervalos disjuntos (Interval Merging). Emplea flujos funcionales, estructuras de datos enlazadas (LinkedList) de alto rendimiento temporal y resolución segura de opcionales para consolidar solapamientos espaciales.
* **IngredientRange**: Entidad geométrica (Value Object inmutable). Ahora implementa la interfaz Comparable para dotarse de ordenamiento natural. Retiene la responsabilidad exclusiva de resolver su propia extensión geométrica mediante el método capacity(), adhiriéndose al principio Tell, Don't Ask.
