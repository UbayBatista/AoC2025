# Día 10: Factory (Parte A)

## 1. Descripción
El reto de la Parte A exige determinar el número mínimo de pulsaciones de botones necesario para configurar correctamente el estado de un conjunto de máquinas en una fábrica. Cada máquina posee una serie de indicadores luminosos y botones que actúan como conmutadores (toggles) sobre un subconjunto específico de luces. Matemáticamente, el problema se modela como la resolución de un sistema de ecuaciones lineales subdeterminado sobre el cuerpo finito $GF(2)$, donde el objetivo es encontrar el vector solución de menor peso (mínima cantidad de variables activas) dentro del espacio de soluciones posibles.

## 2. Metodología
La solución se ha diseñado empleando una arquitectura orientada al dominio y un motor matemático puramente funcional. Se divide en los siguientes componentes principales:
* **Factory, Machine y Button**: Modelo de dominio inmutable (implementado mediante `records`) que encapsula la estructura física de la fábrica. Se emplea el principio *Tell, Don't Ask*, donde la `Factory` asume la orquestación del cálculo total delegando en sus componentes, en lugar de actuar como un mero contenedor de datos (modelo anémico).
* **MachineParser**: Componente encargado del análisis léxico mediante expresiones regulares (Regex), aislando el procesamiento de las cadenas de texto del modelo de negocio.
* **EquationSystem y GaussianSolver**: Motor de álgebra lineal. `EquationSystem` encapsula el estado matricial asegurando inmutabilidad profunda. `GaussianSolver` aplica el algoritmo de eliminación de Gauss-Jordan mediante recursividad funcional y evalúa el espacio nulo (espacio de variables libres) utilizando álgebra de bits para garantizar el hallazgo del mínimo global de pulsaciones.

## 3. Fundamentos y Principios
* **CAMA y SOLID**:
    * **Responsabilidad Única (SRP)**: La lógica matemática de eliminación gaussiana (`GaussianSolver`), la representación de la matriz (`EquationSystem`) y la ingesta de datos (`MachineParser`) están estrictamente separadas, garantizando una alta cohesión.
    * **Inmutabilidad Profunda**: Se ha blindado el estado mediante copias defensivas (`List.copyOf` y clonación de matrices en `EquationSystem`). Ningún método produce efectos secundarios, devolviendo siempre nuevas instancias de la matriz para preservar la pureza funcional.
* **Clean Code**:
    * **YAGNI (You Aren't Gonna Need It)**: El analizador léxico ignora deliberadamente los requerimientos de voltaje (`{...}`) especificados en la entrada, al no ser necesarios para la lógica actual, evitando el sobre-diseño.
    * **Erradicación de la Obsesión por los Primitivos (Good Naming)**: La encapsulación de la matriz primitiva `int[][]` dentro de `EquationSystem` dota al código de semántica, permitiendo exponer un contrato basado en el dominio matemático (ej. `swapRows`, `eliminate`) y ocultando los detalles de implementación de bajo nivel.
* **Paradigma Funcional y Algoritmia**:
    * Se han sustituido completamente los bucles imperativos por la API de `Streams` (`IntStream`).
    * Se utiliza la mónada `Optional` en la búsqueda de pivotes para eliminar la ramificación condicional basada en comprobaciones de nulidad o valores de error (ej. `-1`), logrando un flujo de control declarativo mediante *method chaining* (`.map()`, `.orElseGet()`).