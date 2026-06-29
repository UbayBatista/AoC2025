# Día 11: Reactor (Parte B)

## 1. Descripción
El reto de la Parte B introduce restricciones de obligatoriedad en el recorrido de la topología del reactor. Se requiere encontrar el número total de rutas desde el servidor (`svr`) hasta la salida (`out`), con la condición estricta de que los datos atraviesen tanto el convertidor digital a analógico (`dac`) como la transformada rápida de Fourier (`fft`).

Dado que el grafo es altamente conexo, el cálculo por fuerza bruta incurre en un problema de explosión combinatoria, donde el número de sub-caminos redundantes crece de forma exponencial tendiendo a una complejidad temporal de $O(2^{|V|})$. Esto exige una optimización estructural que permita la resolución matemática en un tiempo lineal o polinomial sin comprometer la evaluación funcional de los nodos.

## 2. Metodología
Se ha optado por implementar una arquitectura segregada en un nuevo contexto delimitado (`software.aoc.day11.b`), cumpliendo con el Principio Abierto/Cerrado (OCP) respecto a la Parte A. La solución algorítmica se fundamenta en un modelo de **Programación Dinámica mediante Memoización**, apoyado en los siguientes componentes:

* **DeviceGraph y DeviceGraphParser**: Reutilizados por diseño (DRY), actúan como el modelo inmutable del reactor y el motor de traducción léxica mediante la API de Streams.
* **ConstrainedPathCounter**: Fachada principal del recorrido. Encapsula y aísla la complejidad mediante la delegación estricta de las operaciones de cálculo.
* **RecursivePathFinder (Method Object)**: Clase interna que actúa como un objeto de método para mitigar el anti-patrón *Long Parameter List*. Transforma los parámetros constantes de la búsqueda (grafo, objetivo, nodos requeridos) en un estado final de instancia y gestiona de forma segura la caché de memoización.
* **PathContext**: Un `record` inmutable anidado que encapsula la posición y el estado booleano de la satisfacción de requisitos, actuando como clave compuesta determinista para la tabla hash de la caché.

## 3. Fundamentos y Principios
* **Principio de Único Nivel de Abstracción (SLAP)**: La lógica recursiva en `RecursivePathFinder` se divide en micro-métodos (`findFrom`, `isTargetReached`, `getCachedOrCompute`). Cada método se lee secuencialmente, ocultando los detalles de implementación de la API de colecciones subyacente.
* **Tell, Don't Ask**: En lugar de consultar propiedades booleanas de `PathContext` y realizar aserciones fuera de la entidad, el método `findFrom` delega la comprobación interrogando a la entidad mediante el método `hasVisitedAllRequirements()`, mejorando la cohesión.
* **Encapsulamiento del Mutador (Seguridad en Memoización)**: Se evita el uso de operaciones concurrentes inseguras dentro de Streams (como `computeIfAbsent`). La tabla `cache` muta de forma estrictamente secuencial y explícita, confinada al ámbito privado del objeto de búsqueda, manteniendo la transparencia referencial de cara al cliente `Main`.
* **Inmutabilidad Evolutiva**: El avance en la recursión (`traverseTo`) no muta el `PathContext` actual, sino que proyecta una nueva instancia, blindando el recorrido contra efectos secundarios cruzados entre diferentes ramas del árbol del grafo.