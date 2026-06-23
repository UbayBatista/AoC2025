# Día 04: Printing Department (Parte A)

## 1. Descripción
El reto de la Parte A consiste en identificar, dentro de un almacén bidimensional, cuántos rollos de papel (`@`) resultan "accesibles" para las carretillas elevadoras. La regla de negocio determina que un rollo es accesible si tiene estrictamente menos de cuatro rollos en sus ocho posiciones adyacentes. El objetivo del dominio es procesar la cuadrícula y devolver este recuento total.

## 2. Metodología
Se aplicó estrictamente el ciclo **TDD (Test-Driven Development)**. En la fase RED, se diseñó una suite de pruebas con JUnit y AssertJ que incluyó el escenario del enunciado y pruebas de frontera (rollo aislado y rollo bloqueado), asegurando la localización de defectos (*Defect Localization*). En la fase GREEN, se implementó la solución adoptando desde el principio un enfoque funcional con la API de `Streams` para garantizar la inmutabilidad de los datos.

## 3. Fundamentos y Principios
* **CAMA**: La **alta cohesión** se consigue al separar la lógica geométrica en el `record` `Coordinate` y la evaluación del conjunto en `PaperGrid`. El **bajo acoplamiento** se asegura mediante el uso de objetos inmutables sin efectos secundarios.
* **SOLID**:
    * **SRP (Responsabilidad Única)**: Existe una separación estricta; `Main` coordina exclusivamente la infraestructura de entrada/salida, mientras que `PaperGrid` evalúa la lógica del dominio.
    * **Ley de Demeter (LoD)**: `Coordinate` calcula sus adyacencias internamente (`neighbors()`), impidiendo que `PaperGrid` manipule sus ejes `x` e `y` para realizar operaciones geométricas.
* **Clean Code**:
    * Se empleó el patrón **Factory Method** (`PaperGrid.from()`) para abstraer la complejidad del *parsing* espacial.
    * El dominio se estructuró en **micro-métodos** (`isAccessible`), operando en un único nivel de abstracción con nombres descriptivos (*Good Naming*).
    * Se aplicó programación defensiva en la clase `Main`, bloqueando su instanciación con un constructor privado que lanza `UnsupportedOperationException`.
* **Paradigma Funcional y Streams API**: Se sustituyó la iteración imperativa clásica por un enfoque declarativo. Se empleó `IntStream.range` y `flatMap` para proyectar el espacio bidimensional de forma inmutable, resolviendo el conteo final mediante predicados (`filter`) y el operador terminal `.count()`.