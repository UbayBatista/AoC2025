# Día 03: Lobby (Parte B)

## 1. Descripción
La Parte B del reto introduce una modificación crítica en la regla de negocio: el sistema requiere ahora encender exactamente doce baterías por banco, manteniendo su orden posicional original. Este cambio incrementa drásticamente la complejidad combinatoria y exige que el voltaje resultante se modele mediante un número entero de 12 dígitos. La agregación y los tipos de retorno del dominio utilizan el tipo primitivo de 64 bits (`long`) para prevenir el desbordamiento aritmético (*overflow*).

## 2. Metodología
Se mantuvo el ciclo **TDD (Test-Driven Development)**, creando una suite de pruebas parametrizadas enfocadas en las cadenas de 12 dígitos. La implementación se desarrolló en el paquete `software.aoc.day03.b`, definiendo una solución algorítmica específica e independiente para esta segunda parte del reto.

## 3. Fundamentos y Principios
* **Domain-Driven Design (DDD) y CAMA**: Se alcanzó una **alta cohesión** segregando el dominio en cuatro artefactos altamente especializados:
    * `BatteryBank`: Entidad de dominio que encapsula los datos y valida su propio estado.
    * `SelectionState`: *Value Object* inmutable que representa el estado matemático de la búsqueda en un instante dado.
    * `MaxSequenceSelector`: Servicio de dominio encargado puramente de la orquestación del algoritmo.
    * `JoltageCalculator`: Fachada (*Facade*) que aísla la complejidad del subdominio de la capa de infraestructura.
* **SOLID**:
    * **SRP (Responsabilidad Única)**: Cada clase se ha diseñado con un único motivo de cambio, delegando las validaciones y transiciones de estado a componentes independientes y especializados.
* **Clean Code**:
    * **Inmutabilidad Estricta**: El uso de `record` para modelar estados asegura la ausencia de efectos secundarios (*side-effects*).
    * **Ausencia de Estructuras Imperativas**: Se diseñó la solución prescindiendo del uso de sentencias de control imperativas (`if`, `for`, `while`), alcanzando una pureza arquitectónica y reduciendo la complejidad ciclomática de los componentes.
* **Paradigma Funcional y Streams API**:
    * La validación de los datos se implementó directamente mediante la mónada `Optional` combinada con un predicado `.filter()`.
    * El algoritmo de extracción se basó en una **evaluación perezosa (*lazy evaluation*)** empleando `Stream.iterate()`. Esto permite calcular las transiciones de estado de forma secuencial y detenerse exactamente tras $K$ iteraciones utilizando `.skip().findFirst()`, demostrando un uso avanzado del paradigma declarativo.