# Día 11: Reactor (Parte B)

## 1. Descripción
El reto de la Parte B introduce restricciones de obligatoriedad en el recorrido de la topología del reactor. Se requiere encontrar el número total de rutas desde el servidor (`svr`) hasta la salida (`out`), con la condición estricta de que los datos atraviesen tanto el convertidor digital a analógico (`dac`) como la transformada rápida de Fourier (`fft`).

Dado que el grafo es altamente conexo, el cálculo por fuerza bruta incurre en un problema de explosión combinatoria, donde el número de sub-caminos redundantes crece de forma exponencial tendiendo a una complejidad temporal de $O(2^{|V|})$. Esto exige una optimización estructural que permita la resolución matemática en un tiempo lineal o polinomial sin comprometer la evaluación funcional de los nodos.

## 2. Metodología
Se ha implementado una arquitectura segregada en un nuevo contexto delimitado, cumpliendo con el principio OCP. La solución algorítmica se fundamenta en la **Programación Dinámica**, transformando el árbol de búsqueda en un Grafo Acíclico Dirigido mediante el almacenamiento en caché de estados intermedios.

## 3. Tests
La suite `ConstrainedPathCounterTest` valida que el motor de búsqueda respete estrictamente los requisitos de nodo:
* **Validación de Completitud**: `should_count_only_paths_visiting_both_dac_and_fft` confirma que el contador solo retorna rutas que satisfacen la conjunción lógica (DAC AND FFT).
* **Validación de Carencia**: Verifica que rutas que alcanzan el destino (`out`) pero omiten requisitos sean descartadas.
* **Validación de Bloqueo**: Confirma que rutas que cumplen requisitos pero no llegan al objetivo terminen en `0L`.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y DDD**:
    * **Alta Cohesión**: El sistema se ha segregado en componentes especializados: `DeviceGraph` como modelo, `ConstrainedPathCounter` como fachada, y `RecursivePathFinder` como objeto de método (*Method Object*) que gestiona la memoización.
* **Clean Code**:
    * **Single Level of Abstraction (SLAP)**: La recursión se divide en micro-métodos (`findFrom`, `isTargetReached`, `getCachedOrCompute`), delegando los detalles de gestión del caché y permitiendo una lectura del "qué" y no del "cómo".
    * **Seguridad en Memoización**: Se evita el uso de `computeIfAbsent` dentro de flujos paralelos o complejos para mantener un control determinista y secuencial sobre el caché privado de la búsqueda.
* **Paradigma Funcional y Algoritmia**:
    * **Programación Dinámica (Memoización)**: Se utiliza `Map<PathContext, Long>` para almacenar resultados previos. El `record PathContext` encapsula el estado (`current`, `visitedReq1`, `visitedReq2`), convirtiendo el historial de visita en una clave hash inmutable y determinista.
    * **Inmutabilidad Evolutiva**: El avance en la recursión (`traverseTo`) no muta el `PathContext`, sino que proyecta una nueva instancia, blindando el recorrido contra efectos secundarios.

## 5. Diagrama UML
![Diagrama UML Dia 11 - Parte B](images/day11-b.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada. Orquesta la lectura de datos e invoca a la fachada ConstrainedPathCounter con los nodos de inicio, fin y requisitos específicos.
* **ConstrainedPathCounter**: Fachada de servicio. Aísla la inicialización del contexto y el objeto de búsqueda de la lógica de negocio cliente.
* **RecursivePathFinder**: Clase interna de método (Method Object). Gestiona el algoritmo DFS y la tabla de memoización (cache), manteniendo el estado de búsqueda encapsulado y fuera de la vista pública.
* **PathContext**: Entidad de estado (Value Object). Representa la posición actual y el progreso en la satisfacción de requisitos. Su inmutabilidad es clave para la correctitud del caché.
* **DeviceGraph**: Modelo del grafo. Provee acceso seguro a la topología, utilizado aquí para obtener las salidas posibles desde un dispositivo.