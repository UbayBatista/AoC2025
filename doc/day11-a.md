# Día 11: Reactor (Parte A)

## 1. Descripción
El reto de la Parte A plantea un problema de flujo y conectividad de señales dentro del sistema de distribución eléctrica de un reactor toroidal. Estructuralmente, el enredo de cables y dispositivos se modela de forma abstracta como un Grafo Dirigido Acíclico (DAG, por sus siglas en inglés) $G = (V, E)$, donde los vértices $V$ representan los dispositivos individuales y las aristas dirigidas $E$ definen la unidireccionalidad en el transporte de datos.

El objetivo del problema consiste en computar la cardinalidad de todos los caminos simples posibles que conecten de manera exacta el nodo raíz de origen $s = \text{"you"}$ con el nodo sumidero terminal $t = \text{"out"}$. Dado que los datos fluyen únicamente en un sentido y el sistema imposibilita el retroceso, se garantiza la ausencia de ciclos estructurales, permitiendo un análisis combinatorio exhaustivo sobre el espacio nulo de transiciones.

## 2. Metodología
Se ha estructurado la solución bajo un desacoplamiento estricto empleando **TDD (Test-Driven Development)**, encapsulando las responsabilidades de dominio, análisis léxico y procesamiento algorítmico en componentes independientes. Las pruebas se enfocaron en validar la robustez estructural del recorrido frente a ramas truncadas y nodos faltantes, garantizando una alta tolerancia a fallos.

## 3. Tests
Las pruebas dividen la lógica algorítmica de la lógica estructural:
* **`DeviceGraphParserTest`**: Verifica el componente léxico asegurando que las cadenas de texto del tipo `"origen: destino1 destino2"` se transformen y mapeen correctamente hacia la estructura del grafo en memoria.
* **`PathCounterTest`**: Aísla el motor de búsqueda en profundidad (DFS). Valida tres escenarios críticos:
    * El conteo correcto del total de caminos en un grafo hiperconectado.
    * El retorno determinista (`0L`) ante ramas sin salida (inexistencia de camino al objetivo).
    * La tolerancia a fallos frente a un nodo de origen inexistente, demostrando la solidez del diseño defensivo del grafo.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y Ley de Deméter (LoD)**:
    * **Alto Desacoplamiento**: El algoritmo de conteo (`PathCounter`) ignora la implementación interna de almacenamiento del mapa. Únicamente invoca el contrato público `getOutputs()` de `DeviceGraph`. Esto permite alterar la topología interna sin inducir impactos colaterales en la capa matemática.
* **Clean Code**:
    * **Expresividad (Good Naming)**: Variables genéricas han sido sustituidas por `unparsedConnections`, `connectionDefinition` y `sourceAndOutputs`, facilitando la autodescripción del código.
    * **Diseño Defensivo (Fail-Safe)**: La gestión de nodos sin salida se resuelve inyectando defensivamente un `Collections.emptyList()` a través de `getOrDefault`. Esto evita condicionales extra en el algoritmo de búsqueda y previene excepciones de nulidad en tiempo de ejecución.
* **Paradigma Funcional y Grafos**:
    * **DFS Puramente Declarativo**: La Búsqueda en Profundidad (Depth-First Search) se ejecuta sin un solo bucle imperativo (`for`, `while`) o variable mutadora. La recursión delega el recuento de rutas al operador `sum()` sobre un flujo plano mapeado (`mapToLong`).
    * **Inmutabilidad de Diccionarios**: El parser hace uso de `Collectors.toUnmodifiableMap()`, sellando la topología del reactor para que permanezca invariante y protegida contra efectos secundarios durante toda la evaluación en memoria.

## 5. Diagrama UML
![Diagrama UML Dia 11 - Parte A](images/day11-a.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Orquesta el flujo global aislando por completo las operaciones de entrada/salida (I/O) de archivos, inyectando un flujo depurado (sin líneas en blanco) en el parser.
* **PathCounter**: Motor de cálculo algorítmico estático. Implementa una estrategia de Búsqueda en Profundidad (DFS) recursiva de carácter puramente declarativo. El algoritmo evalúa el árbol de expansión del grafo sumando los caminos terminales válidos mediante el encadenamiento de funciones matemáticas de Streams.
* **DeviceGraphParser**: Analizador léxico especializado de responsabilidad única (SRP). Aplica transformaciones lineales para convertir el listado de conexiones de texto crudo en la estructura fuertemente tipada inmutable requerida por el dominio.
* **DeviceGraph**: Entidad de dominio implementada mediante un record inmutable. Encapsula internamente el mapa topológico de adyacencias del reactor y expone un contrato público defensivo que provee los nodos conectados o colecciones vacías ante vértices terminales.