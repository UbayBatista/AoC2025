# Día 08: Playground (Parte B)

## 1. Descripción
La Parte B del reto introduce una evolución en el objetivo topológico del dominio: en lugar de identificar clústeres aislados, se requiere unificar la totalidad del grafo en un único circuito global. Matemáticamente, este problema se traduce en el cálculo del Árbol de Expansión Mínima (Minimum Spanning Tree - MST). El objetivo final es identificar la arista exacta (conexión) que logra la conectividad total del sistema —la conexión $N-1$ en un grafo de $N$ vértices— y calcular el producto escalar de las coordenadas X de los dos nodos (`JunctionBox`) que conforman dicha arista.

## 2. Metodología
Se mantuvo el ciclo estricto **TDD** (Red/Green/Refactor). Para garantizar un aislamiento total, se diseñó una suite de pruebas específica en el paquete `b`. Estas pruebas validaron de forma independiente la capacidad de la estructura de datos para reportar alteraciones en la topología (uniones efectivas frente a redundantes) y la correcta orquestación del algoritmo de expansión mínima, asegurando la prevención de regresiones respecto a la Parte A.

## 3. Fundamentos y Principios
* **CAMA y SOLID**:
    * **Modularidad y Prevención de Acoplamiento Espurio**: Las entidades del dominio se aislaron completamente en el paquete `software.aoc.day08.b`. Esta decisión arquitectónica garantiza que ambos dominios (Parte A y Parte B) posean un ciclo de vida independiente, permitiendo la evolución de las reglas de negocio de la Parte B sin afectar a la cohesión de la Parte A.
    * **Principio de Abierto/Cerrado (OCP)**: La clase `CircuitTracker` se diseñó de manera que su método `union` retorne un valor `boolean`. Esto extiende la funcionalidad del componente, permitiendo al llamador conocer si la operación resultó en una fusión efectiva de conjuntos disjuntos, sin alterar la responsabilidad interna de la estructura de datos.
* **Clean Code**:
    * **SLAP (Single Level of Abstraction Principle)**: En la clase `Playground`, la orquestación principal del MST se estructuró delegando los detalles técnicos a micro-métodos puros como `isEffectiveConnection` y `computeXProduct`. Esto garantiza que la lectura del método `calculateLastConnectionXProduct` exprese exclusivamente la lógica de negocio.
    * **Variables Explicativas (Good Naming)**: Se eliminó la presencia de "números mágicos" o fórmulas algebraicas crudas en el flujo de datos. La instanciación de la variable `requiredConnections` (calculada como $V - 1$) documenta formalmente la teoría de grafos subyacente de forma unívoca y clara.
* **Paradigma Funcional y Algoritmia Avanzada**:
    * **Algoritmo de Kruskal mediante Streams**: La resolución del Árbol de Expansión Mínima se implementó utilizando un *pipeline* declarativo puro. Las aristas, previamente ordenadas por distancia, son evaluadas secuencialmente mediante la operación `filter`. El uso de `skip(requiredConnections - 1)` permite omitir matemáticamente todas las aristas necesarias excepto la última, resolviendo la iteración sin recurrir a estructuras de control imperativas (bucles `while` o contadores).
    * **Mónadas y Ausencia de Bifurcaciones lógicas**: La evaluación de las raíces dentro del gestor de circuitos se maneja a través de la mónada `Optional`. Esto elimina cualquier dependencia de sentencias `if-else`, garantizando un flujo determinista y continuo de evaluación de datos.