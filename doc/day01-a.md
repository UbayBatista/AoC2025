# Día 01: Secret Entrance (Parte A)

## 1. Descripción
El reto consiste en simular el movimiento de un dial de seguridad circular para descifrar una contraseña basada en el número de veces que el dial apunta al valor 0. La lógica requiere una correcta gestión de aritmética modular y el procesamiento de una secuencia de rotaciones.

## 2. Metodología
Se ha aplicado la metodología **TDD (Test-Driven Development)** mediante un ciclo estricto de Red-Green-Refactor. El desarrollo se inició con la creación de pruebas unitarias automatizadas para definir el comportamiento matemático y léxico del sistema. Esta aproximación ha forzado un diseño altamente modular, donde la lógica de negocio se implementó sin depender en absoluto de la capa de infraestructura (lectura de archivos).

## 3. Tests
Las pruebas se aislaron de manera granular para garantizar una correcta localización de defectos (*Defect Localization*):
* **`RotationParserTest`**: Verifica el análisis léxico de las cadenas de entrada. Evalúa de forma independiente la correcta extracción del signo (dirección `L`/`R`) y la magnitud numérica.
* **`DialStateTest`**: Protege la invariante matemática del dominio. Verifica las transiciones de estado inmutables, la correcta normalización mediante aritmética modular (previniendo desbordamientos negativos) y el incremento condicional del contador de ceros.
* **`SafeDialTest`**: Prueba de integración de la orquestación. Valida que el flujo de datos global (`Stream`) y la acumulación de rotaciones (`reduce`) devuelvan la contraseña final correcta.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA**: Se ha buscado una **alta cohesión** delegando el parseo y la lógica de estado en clases específicas, y un **bajo acoplamiento** minimizando la visibilidad de los métodos internos (uso exhaustivo de la visibilidad `private`).
* **SOLID**:
  * **SRP (Responsabilidad Única)**: `RotationParser` actúa únicamente como analizador léxico, `DialState` como entidad matemática inmutable, y `SafeDial` como servicio orquestador.
  * **DIP (Inversión de Dependencias)**: `SafeDial` mantiene su política de alto nivel limpia. Aunque no emplea interfaces clásicas, delega los comportamientos delegando en abstracciones funcionales de la API de Streams y en clases de soporte especializadas, evitando gestionar directamente los detalles de implementación de bajo nivel.
* **Clean Code**:
  * **Micro-métodos**: Se ha priorizado la extracción de lógicas a funciones de una sola línea (`signOf`, `valueOf`, `initialState`) para asegurar que el código comunique su intención (*Good Naming*), eliminando la necesidad de comentarios.
  * **Diseño Defensivo**: Las clases que no deben mantener estado (`Main`, `RotationParser`) han sido bloqueadas estructuralmente mediante constructores privados que lanzan `UnsupportedOperationException`.
* **Paradigma Funcional e Inmutabilidad**: La entidad `DialState` es un `record` puramente inmutable. Toda la iteración del dominio se resuelve de forma declarativa mediante `stream().map().reduce()`, eliminando bucles imperativos y variables mutables.

## 5. Diagrama UML
![Diagrama UML Dia 01 - Parte A](images/day01-a.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Es responsable exclusivamente de la capa de infraestructura (lectura del sistema de archivos I/O) y de orquestar la inyección de la entrada sin procesar hacia la capa de dominio.
* **SafeDial**: Servicio principal de dominio. Actúa como orquestador de alto nivel aplicando un flujo declarativo (Pipeline de Streams) para transformar el listado de cadenas de entrada en un estado matemático final.
* **RotationParser**: Clase utilitaria y pura (sin estado). Su única responsabilidad es el análisis léxico: toma una directiva de texto bruta (por ejemplo, "L5") y la transforma en una magnitud entera direccional (-5).
* **DialState**: Entidad central (Value Object) del modelo de dominio. Modelada como un record, garantiza inmutabilidad absoluta. Posee funciones puras matemáticas para calcular la transición temporal del dial, aplicando reglas de aritmética modular sin provocar efectos secundarios.