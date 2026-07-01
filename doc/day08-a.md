# Día 08: Playground (Parte A)

## 1. Descripción
La Parte A del reto plantea un problema de optimización topológica y de teoría de grafos. Dada una lista de coordenadas tridimensionales que representan cajas de conexiones, el objetivo es establecer enlaces físicos entre ellas priorizando las distancias más cortas en línea recta. Se requiere procesar de forma estricta las 1000 conexiones más próximas. Matemáticamente, las cajas representan los vértices de un grafo y las distancias sus aristas. La solución exige modelar la formación de estos circuitos, resolver las uniones y aislar los clústeres resultantes para calcular el producto escalar de las cardinalidades de los tres circuitos de mayor tamaño.

## 2. Metodología
El desarrollo se guió mediante el ciclo estricto **TDD (Test-Driven Development)** (Red/Green/Refactor). Para favorecer la localización precisa de defectos (*Defect Localization*) y asegurar una evaluación granular, las pruebas se segmentaron en tres unidades arquitectónicas completamente independientes. Esta división asegura que un fallo en el dominio geométrico 3D no contamine la evaluación de la algoritmia de conjuntos conexos, y viceversa.

## 3. Tests
Las aserciones se aislaron para evaluar componentes puramente algorítmicos frente a componentes de integración:
* **`JunctionBoxTest`**: Valida el *parsing* defensivo y verifica que la matemática espacial (distancia euclidiana al cuadrado) se calcule correctamente utilizando aritmética entera.
* **`CircuitTrackerTest`**: Aísla la estructura de datos pura. Verifica exhaustivamente la lógica de unión (incluyendo uniones redundantes que no deben alterar el tamaño del clúster) y asegura que el top $N$ de los tamaños de los circuitos se devuelva correctamente ordenado.
* **`PlaygroundTest`**: Prueba de integración algorítmica. Valida que el combinador cartesiano construya todas las aristas posibles, las ordene correctamente por distancia, limite el flujo y acumule el producto escalar final.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **Algoritmia Avanzada (DSU)**:
  * Para la gestión de circuitos, se implementó la estructura *Disjoint Set Union (Union-Find)* apoyada en heurísticas de compresión de caminos (*path compression*) y unión por tamaño (*union by size*). Esta técnica asegura una complejidad temporal casi constante $O(\alpha(n))$ en la resolución de conjuntos conexos.
* **CAMA y SOLID**:
  * **Alta Cohesión y SRP**: El diseño se particionó en entidades altamente focalizadas. `JunctionBox` es un modelo matemático puro 3D. `Connection` actúa como arista unidireccional. `CircuitTracker` encapsula exclusivamente la lógica matemática de árboles conexos, y `Playground` orquesta los flujos.
  * **Ley de Deméter (LoD)**: `Playground` interactúa con el orquestador de circuitos a través de interfaces explícitas (comandos `union` y consultas), sin tener acceso a los arrays primitivos internos (`parent`, `size`).
* **Clean Code y Optimización**:
  * **Zero-IF Policy (Ausencia de Bifurcaciones Imperativas)**: Para alcanzar un diseño puramente declarativo y reducir la complejidad ciclomática, se eludieron las sentencias `if`. El filtrado seguro de raíces en `CircuitTracker` se resolvió mediante la mónada `Optional.filter().ifPresent()`, y las asignaciones mediante operadores ternarios.
  * **Optimización Aritmética**: Se evitó la operación `Math.sqrt` (y los errores de punto flotante asociados a `double`), utilizando el cuadrado de la distancia para las comparaciones (`distanceSquared`).
* **Paradigma Funcional (Streams)**:
  * El producto cartesiano (las combinaciones cruzadas de todos los vértices del grafo) se implementó sin bucles anidados `for` encapsulando índices mediante `IntStream.range` y `flatMap`.

## 5. Diagrama UML
![Diagrama UML Dia 08 - Parte A](/images/day08.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada a la aplicación. Centraliza la infraestructura de acceso a los ficheros locales e inyecta la lista de datos crudos directamente hacia el entorno de simulación del dominio.
* **Playground**: Servicio de orquestación principal. Actúa como gestor del grafo. Se encarga de aplicar combinatoria funcional para generar todas las conexiones posibles (aristas), ordenarlas por proximidad, e iterar sobre el límite establecido enviando comandos de unión al rastreador de circuitos.
* **CircuitTracker**: Estructura de datos avanzada (Disjoint Set Union). Mantiene el registro de los nodos y sus agrupaciones mediante representaciones de árboles en arrays primitivos. Implementa heurísticas de optimización (path compression y union by size) garantizando un rendimiento matemático óptimo.
* **Connection**: Entidad de relación inmutable (Value Object). Modela una arista entre dos nodos del grafo. Implementa la interfaz Comparable para dotar al dominio de una heurística natural de ordenación matemática basada en la magnitud del cuadrado de su distancia.
* **JunctionBox**: Entidad espacial tridimensional (Value Object). Modelada mediante un record, asegura inmutabilidad. Posee lógica de validación léxica de sus propias coordenadas y encapsula el álgebra necesaria para calcular la distancia euclidiana hacia otras cajas sin comprometer el rendimiento con operaciones de coma flotante.