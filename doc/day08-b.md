# Día 08: Playground (Parte B)

## 1. Descripción
La Parte B del reto introduce una evolución en el objetivo topológico del dominio: en lugar de identificar clústeres aislados, se requiere unificar la totalidad del grafo en un único circuito global. Matemáticamente, este problema se traduce en el cálculo del Árbol de Expansión Mínima (Minimum Spanning Tree - MST). El objetivo final es identificar la arista exacta (conexión) que logra la conectividad total del sistema —la conexión $N-1$ en un grafo de $N$ vértices— y calcular el producto escalar de las coordenadas X de los dos nodos (`JunctionBox`) que conforman dicha arista.

## 2. Metodología
Se mantuvo el ciclo estricto **TDD (Test-Driven Development)** (Red/Green/Refactor). Para garantizar un aislamiento total, se diseñó una suite de pruebas específica validando de forma independiente la capacidad de la estructura de datos para reportar alteraciones en la topología (uniones efectivas frente a redundantes) y la correcta orquestación del algoritmo de expansión mínima, asegurando la prevención de regresiones.

## 3. Tests
Las aserciones se ampliaron para validar la detección de ciclos y la completitud del grafo:
* **Prueba de Ciclos en DSU**: Se añadió `should_return_true_on_effective_union_and_false_on_redundant_union` en `CircuitTrackerTest` para verificar que la estructura informe correctamente cuándo una arista une dos componentes disjuntos (efectiva) y cuándo conecta nodos del mismo componente (redundante/ciclo).
* **Prueba de Integración MST**: `should_calculate_x_product_of_final_connection_to_complete_circuit` valida el flujo completo del Algoritmo de Kruskal, asegurando que el motor algorítmico se detenga exactamente en la arista que logra la conectividad total y aplique la operación matemática requerida (producto de X).

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Principio de Abierto/Cerrado (OCP)**: La clase `CircuitTracker` se diseñó de manera que su método `union` retorne un valor `boolean`. Esto extiende la funcionalidad del componente, permitiendo al llamador conocer si la operación resultó en una fusión efectiva sin alterar la responsabilidad interna de la estructura ni romper la compatibilidad con el código de la Parte A.
* **Clean Code**:
  * **SLAP (Single Level of Abstraction Principle)**: En la clase `Playground`, la orquestación principal del MST se estructuró delegando los detalles técnicos a micro-métodos puros como `isEffectiveConnection` y `computeXProduct`.
  * **Variables Explicativas (Good Naming)**: Se eliminó la presencia de "números mágicos" o fórmulas algebraicas crudas. La instanciación de la variable `requiredConnections` documenta formalmente la teoría de grafos subyacente (un árbol de $V$ vértices requiere $V - 1$ aristas) de forma unívoca y clara.
* **Paradigma Funcional y Algoritmia Avanzada**:
  * **Algoritmo de Kruskal mediante Streams**: La resolución del Árbol de Expansión Mínima se implementó utilizando un *pipeline* declarativo puro. Las aristas, previamente ordenadas por distancia, son evaluadas secuencialmente mediante la operación `filter`. El uso de `skip(requiredConnections - 1)` permite omitir matemáticamente todas las aristas necesarias excepto la última, resolviendo la iteración sin recurrir a estructuras de control imperativas.

## 5. Diagrama UML
![Diagrama UML Dia 08 - Parte B](/images/day08.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada a la aplicación. Centraliza la infraestructura de acceso a los ficheros locales e inyecta la lista de datos crudos directamente hacia el entorno de simulación del dominio.
* **Playground**: Servicio de orquestación principal. Evoluciona para implementar el Algoritmo de Kruskal de forma puramente funcional, empleando saltos matemáticos (skip) sobre el flujo de conexiones generadas para identificar la arista exacta que consolida el Árbol de Expansión Mínima.
* **CircuitTracker**: Estructura de datos avanzada (Disjoint Set Union). Mantiene las heurísticas de optimización (path compression y union by size). Cumpliendo el principio OCP, extiende el comportamiento del comando de unión para emitir un flag booleano, permitiendo a los clientes detectar de forma pasiva la formación de ciclos.
* **Connection**: Entidad de relación inmutable (Value Object). Modela una arista entre dos nodos del grafo, dotando al dominio de ordenamiento natural por distancia cuadrática.
* **JunctionBox**: Entidad espacial tridimensional (Value Object inmutable). Encapsula el álgebra de posicionamiento y expone las coordenadas, utilizadas ahora en la fase terminal para el cómputo del producto escalar transversal.