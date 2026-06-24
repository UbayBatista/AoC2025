# Día 05: Cafeteria (Parte B)

## 1. Descripción
La Parte B del reto introduce una modificación semántica en la interpretación de los datos de inventario. A diferencia de la Parte A, la lista de identificadores disponibles es irrelevante; el objetivo es calcular la cardinalidad total del conjunto unión formado por una serie de rangos numéricos superpuestos. Dado que los intervalos pueden solaparse, el reto se transforma en un problema geométrico 1D de fusión de intervalos disjuntos para calcular la suma total de identificadores únicos frescos.

## 2. Metodología
Se empleó el ciclo **TDD** manteniendo el aislamiento total respecto a la Parte A. Se implementó un algoritmo de ordenación y fusión de rangos (*Interval Merging*) que garantiza la transformación de solapamientos en un conjunto de intervalos disjuntos. La solución integra un paradigma puramente funcional, eliminando estructuras de control imperativas en favor de operaciones de acumulación declarativa mediante la API de `Streams`.

## 3. Fundamentos y Principios
* **CAMA**: Se garantiza la **Alta Cohesión** mediante la especialización de la entidad `IngredientRange`, que encapsula la lógica matemática de su propia capacidad (`capacity()`). El servicio `TotalFreshIngredientCounter` posee una responsabilidad única y coherente: la agregación geométrica de rangos, interactuando con las entidades mediante sus métodos de dominio, no mediante la inspección de sus campos.
* **"Tell, Don't Ask"**: Se ha aplicado rigurosamente este principio para evitar el *Feature Envy* y el acoplamiento excesivo. En lugar de exponer el estado interno de `IngredientRange` (sus campos `start` y `end`) para que servicios externos realicen cálculos geométricos, el servicio `TotalFreshIngredientCounter` solicita al objeto que realice sus propios cálculos mediante `capacity()`. Esto garantiza que la lógica de negocio esté encapsulada dentro de la entidad responsable, facilitando la mantenibilidad y protegiendo la integridad del modelo ante cambios futuros en su estructura interna.
* **SOLID**:
    * **SRP**: El diseño separa estrictamente la infraestructura (lectura y parseo en `InventoryParser`) de la lógica matemática de dominio (`TotalFreshIngredientCounter`).
    * **OCP**: La nueva funcionalidad se encapsula en una clase independiente, evitando cualquier modificación en los componentes de la Parte A, lo cual asegura un sistema resiliente ante cambios.
* **Clean Code**:
    * **Programación Declarativa**: Se ha optado por un estilo de programación puramente funcional. En lugar de estructuras de control imperativas (bucles o condicionales), la lógica de fusión se implementa mediante flujos declarativos utilizando `collect` y la mónada `Optional`. Esto reduce la complejidad ciclomática y mejora la legibilidad del algoritmo.
    * **Uso eficiente de estructuras**: Se utilizó `LinkedList` como `Deque` para optimizar las operaciones de manipulación de rangos (`peekLast`, `removeLast`), garantizando una complejidad temporal eficiente de $O(1)$ en el procesamiento del extremo final del acumulador.
* **Paradigma Funcional**:
    * **Inmutabilidad**: Toda la lógica de fusión genera nuevas instancias de `IngredientRange` o listas inmutables (`List.copyOf`), asegurando que no existan efectos secundarios ni estados mutables compartidos.
    * **Streams Declarativos**: El uso de `takeWhile` para la ingesta de datos y `collect` para la consolidación de rangos demuestra un flujo de datos sin estado intermedio explícito.
