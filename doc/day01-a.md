# Día 01: Secret Entrance (Parte A)

## 1. Descripción
El reto consiste en simular el movimiento de un dial de seguridad circular para descifrar una contraseña basada en el número de veces que el dial apunta al valor 0. La lógica requiere una correcta gestión de aritmética modular y el procesamiento de una secuencia de rotaciones.

## 2. Metodología
Se ha aplicado la metodología **TDD (Test-Driven Development)**, iniciando con la creación de tests unitarios que protegen el comportamiento del sistema antes de la implementación del código de producción. Se utilizaron JUnit 6 y AssertJ para garantizar pruebas deterministas y legibles.

## 3. Fundamentos y Principios
* **CAMA**: Se ha buscado una **alta cohesión** delegando el parseo y la lógica de estado en clases específicas, y un **bajo acoplamiento** minimizando la visibilidad de los métodos internos.
* **SOLID**:
    * **SRP**: `RotationParser` solo parsea, `DialState` solo calcula el estado siguiente, y `SafeDial` solo orquesta.
    * **DIP**: `SafeDial` delega comportamientos a abstracciones y clases de soporte, manteniendo la lógica de alto nivel limpia.
* **Clean Code**: Se ha priorizado el uso de **micro-métodos** (funciones de una sola línea) y el paradigma funcional mediante `Streams` para eliminar la verbosidad y efectos secundarios.