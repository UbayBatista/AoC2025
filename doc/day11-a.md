# Día 11: Reactor (Parte A)

## 1. Descripción
El reto de la Parte A plantea un problema de flujo y conectividad de señales dentro del sistema de distribución eléctrica de un reactor toroidal. Estructuralmente, el enredo de cables y dispositivos se modela de forma abstracta como un Grafo Dirigido Acíclico (DAG, por sus siglas en inglés) $G = (V, E)$, donde los vértices $V$ representan los dispositivos individuales y las aristas dirigidas $E$ definen la unidireccionalidad en el transporte de datos.

El objetivo del problema consiste en computar la cardinalidad de todos los caminos simples posibles que conecten de manera exacta el nodo raíz de origen $s = \text{"you"}$ con el nodo sumidero terminal $t = \text{"out"}$. Dado que los datos fluyen únicamente en un sentido y el sistema imposibilita el retroceso, se garantiza la ausencia de ciclos estructurales, permitiendo un análisis combinatorio exhaustivo sobre el espacio nulo de transiciones.

## 2. Metodología
Para proveer una solución robusta y alineada con los estándares de arquitectura modular de la asignatura, el sistema se ha estructurado desde su concepción bajo un desacoplamiento estricto, encapsulando las responsabilidades de dominio, análisis léxico y procesamiento algorítmico en componentes independientes:

* **DeviceGraph**: Componente central de dominio implementado mediante un `record` inmutable de Java. Encapsula internamente el mapa de adyacencias del reactor (`Map<String, List<String>>`) y expone un contrato público seguro (`getOutputs`) que abstrae la colección subyacente.
* **DeviceGraphParser**: Analizador especializado de una sola responsabilidad (SRP). Aplica operaciones funcionales y transformaciones lineales mediante `Streams` para convertir el listado de conexiones de texto crudo en la estructura fuertemente tipada de `DeviceGraph`.
* **PathCounter**: Motor de cálculo algebraico. Implementa una estrategia de Búsqueda en Profundidad (DFS) recursiva de carácter puramente declarativo. El algoritmo evalúa el árbol de expansión del grafo sumando los caminos terminales válidos mediante el encadenamiento de funciones matemáticas de la API de Streams.
* **Main**: Punto de entrada de la aplicación que orquesta el flujo general aislando por completo las operaciones de entrada/salida (I/O) de la lógica de negocio del reactor.

## 3. Fundamentos y Principios
El diseño de la aplicación destaca por la incorporación nativa de los siguientes pilares técnicos de la ingeniería de software:

* **Alta Cohesión, Bajo Acoplamiento y Ley de Deméter**: El algoritmo de conteo (`PathCounter`) no posee conocimiento de la estructura interna de almacenamiento ni de la tipología del mapa. Solo interroga a la entidad de dominio (`DeviceGraph`) acerca de las salidas de un dispositivo concreto. Esto permite modificar la implementación interna del almacenamiento o la gramática de parseo sin inducir ningún impacto colateral en el componente de cálculo.
* **Inmutabilidad Absoluta**: Se ha erradicado la mutación de estado a lo largo de todo el ciclo de ejecución. La clase parser hace uso de `Collectors.toUnmodifiableMap` y `Stream.toList()`, asegurando que la topología del reactor permanezca invariante y protegida contra efectos secundarios en tiempo de ejecución.
* **Clean Code y Expresividad (Good Naming)**: Se ha combatido la opacidad nominal mediante el uso de términos del dominio del problema. Variables genéricas de tipo (como `lines` o `parts`) han sido sustituidas por `unparsedConnections`, `connectionDefinition` y `sourceAndOutputs`, facilitando la autodescripción del código fuente sin necesidad de comentarios redundantes.
* **Paradigma Funcional y Erradicación de Bucles Imperativos**: La eliminación de estructuras tradicionales `for` y `while` reduce drásticamente la complejidad ciclomática. El recorrido del grafo se unifica de manera limpia mediante `mapToLong` y la operación terminal `sum()`, mientras que la gestión de nodos hoja sin salida se resuelve defensivamente mediante `Collections.emptyList()`, garantizando la localización de defectos y un código tolerante a fallos.