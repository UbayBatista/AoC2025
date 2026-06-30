# Día 03: Lobby (Parte B)

## 1. Descripción
La Parte B del reto introduce una modificación crítica en la regla de negocio: el sistema requiere ahora encender exactamente doce baterías por banco, manteniendo su orden posicional original. Este cambio incrementa drásticamente la complejidad combinatoria y exige que el voltaje resultante se modele mediante un número entero de 12 dígitos. La agregación y los tipos de retorno del dominio utilizan el tipo primitivo de 64 bits (`long`) para prevenir el desbordamiento aritmético (*overflow*).

## 2. Metodología
Se mantuvo el ciclo **TDD (Test-Driven Development)**, creando una suite de pruebas parametrizadas enfocadas en las cadenas de 12 dígitos. La implementación se desarrolló en un subpaquete independiente, promoviendo una evolución arquitectónica orientada al dominio (*Domain-Driven Design*) mediante la creación de múltiples entidades especializadas en la transición matemática de estados.

## 3. Tests
La clase `JoltageCalculatorTest` valida el nuevo contexto acotado garantizando la protección de las invariantes:
* **Pruebas Parametrizadas de Extracción**: Utilizan `@CsvSource` para inyectar configuraciones de baterías complejas y verificar que el motor matemático extraiga correctamente la secuencia de 12 dígitos óptima respetando el orden.
* **Prueba de Integración**: Confirma que el servicio de alto nivel procesa listas enteras y computa la sumatoria utilizando tipos `long` para evitar el truncamiento aritmético.
* **Prueba de Diseño Defensivo**: Protege la invariante de longitud, comprobando que intentar instanciar un banco con menos de 12 dígitos arroje inmediatamente una `IllegalArgumentException`.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y Domain-Driven Design (DDD)**:
  * **Alta Cohesión**: Se segregó el dominio en componentes altamente especializados. `BatteryBank` protege la invariante y actúa como punto de entrada, `MaxSequenceSelector` orquesta el motor funcional, y `SelectionState` modela la invariante matemática transitoria.
  * **Bajo Acoplamiento**: El flujo de datos entre clases es puramente inmutable y direccional.
* **SOLID**:
  * **SRP (Responsabilidad Única)**: Cada `record` tiene una razón única de cambio. La lógica de evaluación de la ventana (`SelectionState`) está totalmente separada de la política de iteración (`MaxSequenceSelector`).
* **Clean Code**:
  * **Erradicación de Estructuras Imperativas**: Se diseñó la solución prescindiendo totalmente de sentencias de control imperativas (`if`, `for`, `while`), alcanzando una pureza arquitectónica excepcional.
* **Paradigma Funcional y Streams API**:
  * **Mónadas en Constructores**: La validación inicial en `BatteryBank` prescinde del tradicional `if (digits == null)` utilizando `Optional.ofNullable(...).filter(...).orElseThrow(...)`.
  * **Evaluación Perezosa (Lazy Evaluation) sin Bucles**: El algoritmo de extracción avanza utilizando `Stream.iterate()`. Transiciona secuencialmente el estado y se detiene exactamente tras $K$ iteraciones con `.skip(targetLength).findFirst()`, un patrón funcional de alto nivel.
  * **Reducción Funcional**: La búsqueda del dígito mayor en la ventana válida se resuelve de forma elegante mediante `IntStream.rangeClosed` acoplado a un `reduce`, preservando el índice del mayor candidato sin recurrir a variables centinela mutables.

## 5. Diagrama UML
![Diagrama UML Dia 03 - Parte B](images/day03-b.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Responsable de la capa de infraestructura, realiza la lectura del archivo de datos bruto y transfiere el listado de cadenas resultantes hacia la capa de dominio.
* **JoltageCalculator**: Fachada o servicio de dominio estático. Coordina la instanciación masiva de los bancos de baterías y aplica operaciones terminales de sumatoria matemática devolviendo un resultado de 64 bits.
* **BatteryBank**: Entidad del dominio principal. Protege su propia invariante garantizando, mediante mónadas, que los datos ingeridos cumplan con el tamaño requerido, sirviendo como proxy seguro hacia el selector de secuencias.
* **MaxSequenceSelector**: Servicio de dominio inmutable orquestador del algoritmo. Emplea flujos iterativos de estado infinito y evaluación perezosa para extraer la secuencia exacta requerida sin utilizar bucles.
* **SelectionState**: Value Object (record) inmutable que encapsula el estado exacto de la selección en un instante dado (lo que queda por procesar, lo que falta por escoger y lo ya seleccionado). Aplica álgebra pura para determinar el dígito óptimo dentro de la ventana de búsqueda, generando transiciones limpias y libres de efectos secundarios.