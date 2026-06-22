# Día 02: Gift Shop (Parte A)

## 1. Descripción
El reto consiste en procesar una serie de rangos numéricos separados por comas para identificar y sumar los identificadores (IDs) de productos inválidos. Un ID se cataloga como inválido si su representación en cadena de texto está formada por una secuencia de dígitos repetida exactamente dos veces de forma consecutiva (por ejemplo, `55` o `123123`). La magnitud de los rangos exige el procesamiento con enteros de 64 bits (`long`) para evitar el desbordamiento aritmético.

## 2. Metodología
Se ha continuado con la aplicación de la metodología **TDD (Test-Driven Development)** utilizando JUnit y AssertJ. Se establecieron inicialmente las pruebas de caja blanca para verificar el predicado lógico de invalidez y las pruebas de integración para el procesamiento completo de los rangos, asegurando un diseño dirigido por el comportamiento esperado antes de la implementación del código de producción.

## 3. Fundamentos y Principios
* **CAMA**: Se ha logrado una **alta cohesión** encapsulando las reglas de validación estructural y simetría en la entidad `GiftId` y la delimitación numérica en la entidad `Range`. El **bajo acoplamiento** se evidencia al procesar flujos de datos puros sin compartir estado mutable.
* **SOLID**:
    * **SRP (Responsabilidad Única)**: Cada clase tiene un propósito atómico. `Range` gestiona la creación de flujos de números, `GiftId` evalúa la validez individual del identificador, e `InvalidGiftIdCalculator` coordina la tubería de cálculo.
    * **KISS y YAGNI**: Se diseñó la clase `Main` para orquestar la lectura de archivos de recursos de forma directa, eliminando sobre-ingeniería e interfaces de E/S innecesarias para el contexto de un algoritmo lineal de un solo uso.
* **Clean Code**:
    * Se ha hecho uso intensivo de **micro-métodos** en `GiftId`, aislando validaciones como la paridad de longitud o la división de cadenas en funciones de una sola línea.
    * La inmutabilidad se ha garantizado utilizando `records` de Java para las entidades del dominio.
    * Se ha implementado el **paradigma funcional** mediante la API de Streams (`flatMapToLong`, `mapToObj`, `filter`, `sum`) para suprimir los bucles imperativos y la instanciación de colecciones intermedias.
    * Se aplicó **diseño defensivo**, bloqueando la instanciación de clases utilitarias (`Main`, `InvalidGiftIdCalculator`) mediante constructores privados.