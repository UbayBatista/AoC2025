# Día 10: Factory (Parte A)

## 1. Descripción
El reto de la Parte A exige determinar el número mínimo de pulsaciones de botones necesario para configurar correctamente el estado de un conjunto de máquinas en una fábrica. Cada máquina posee una serie de indicadores luminosos y botones que actúan como conmutadores (toggles) sobre un subconjunto específico de luces. Matemáticamente, el problema se modela como la resolución de un sistema de ecuaciones lineales subdeterminado sobre el cuerpo finito $GF(2)$, donde el objetivo es encontrar el vector solución de menor peso (mínima cantidad de variables activas) dentro del espacio de soluciones posibles.

## 2. Metodología
La solución se ha diseñado empleando una arquitectura orientada al dominio y un motor matemático puramente funcional guiado por **TDD**. El diseño aísla completamente la ingestión léxica de las expresiones regulares respecto al motor de álgebra lineal, permitiendo probar la eliminación gaussiana y la resolución del espacio nulo de forma estricta y matemática.

## 3. Tests
Las pruebas se diseñaron para aislar el álgebra computacional del *parsing* textual:
* **`MachineParserTest`**: Valida que las expresiones regulares extraigan correctamente el estado objetivo `[...]` y las botoneras `(...)`, verificando además que se ignore activamente (YAGNI) la configuración de voltaje `{...}` no requerida por las reglas de negocio actuales.
* **`GaussianSolverTest`**: Aísla el motor algebraico. Suministra entidades pre-parseadas y valida que la reducción matricial y la exploración combinatoria del espacio nulo devuelvan el mínimo global de pulsaciones exacto para cada máquina.
* **`FactoryTest`**: Prueba de integración que orquesta el flujo completo de una factoría con múltiples máquinas, validando la sumatoria total del sistema.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Responsabilidad Única (SRP)**: La lógica matemática de eliminación gaussiana (`GaussianSolver`), la representación estructural de la matriz (`EquationSystem`) y la ingesta de datos (`MachineParser`) están estrictamente separadas, garantizando una altísima cohesión.
  * **Tell, Don't Ask**: La entidad `Factory` orquesta el cálculo delegando la creación del sistema y su resolución sin inspeccionar el contenido de las máquinas, actuando como un verdadero servicio de dominio.
* **Clean Code**:
  * **YAGNI (You Aren't Gonna Need It)**: El analizador léxico ignora los requerimientos de voltaje para evitar el sobre-diseño y mantener el foco en la regla de negocio presente.
  * **Erradicación de la Obsesión por los Primitivos**: La encapsulación de la matriz primitiva `int[][]` dentro de `EquationSystem` dota al código de semántica, permitiendo exponer un contrato basado en el dominio matemático (ej. `swapRows`, `eliminate`) y ocultando los detalles de implementación en memoria.
* **Paradigma Funcional y Algoritmia Avanzada**:
  * **Inmutabilidad Profunda (Deep Immutability)**: Se ha blindado el estado de `EquationSystem`. Operaciones como `swapRows` o `eliminate` clonan la matriz interna y devuelven nuevas instancias. Ningún método produce efectos secundarios.
  * **Eliminación Gaussiana Recursiva**: Se han sustituido los tradicionales bucles imperativos anidados por recursividad funcional (`reduce(system, pivot, col)`), evaluando el espacio de variables libres combinando álgebra de bits (máscaras y desplazamientos) con flujos de `Streams`.

## 5. Diagrama UML
![Diagrama UML Dia 10 - Parte A](images/day10-a.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Encapsula la infraestructura de lectura de archivos y proyecta las cadenas de texto hacia el dominio ignorando las líneas en blanco.
* **Factory**: Orquestador funcional del dominio. Encapsula la colección de máquinas y coordina la transformación de cada una en un sistema de ecuaciones para su posterior resolución y acumulación de resultados.
* **MachineParser**: Analizador léxico utilitario. Utiliza expresiones regulares (Regex) y la API de Streams para extraer exclusivamente los datos relevantes al problema actual (estado y botones), aplicando el principio YAGNI al descartar la información de voltaje.
* **EquationSystem**: Entidad matemática (Value Object). Envuelve una matriz primitiva bidimensional aplicando inmutabilidad profunda. Expone operaciones matriciales algebraicas (permutación de filas, eliminación mediante XOR) garantizando la pureza funcional al retornar nuevas instancias en cada operación.
* **GaussianSolver**: Motor de álgebra lineal. Implementa el algoritmo de eliminación de Gauss-Jordan sobre el cuerpo finito GF(2) mediante recursión funcional. Explora el espacio nulo de variables libres utilizando máscaras de bits para aislar la combinación que requiere el mínimo absoluto de pulsaciones.
* **Machine y Button**: Entidades de dominio puras (records) que actúan como contenedores inmutables de la configuración física de cada componente de la factoría.