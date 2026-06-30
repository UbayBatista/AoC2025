# Día 02: Gift Shop (Parte A)

## 1. Descripción
El reto consiste en procesar una serie de rangos numéricos separados por comas para identificar y sumar los identificadores (IDs) de productos inválidos. Un ID se cataloga como inválido si su representación en cadena de texto está formada por una secuencia de dígitos repetida exactamente dos veces de forma consecutiva (por ejemplo, `55` o `123123`). La magnitud de los rangos exige el procesamiento con enteros de 64 bits (`long`) para evitar el desbordamiento aritmético.

## 2. Metodología
Se continuó con la aplicación de la metodología **TDD (Test-Driven Development)** utilizando el framework JUnit y aserciones con AssertJ. El diseño comenzó aislando la lógica central de validación mediante pruebas de predicado para verificar la simetría de las cadenas, progresando posteriormente hacia las pruebas de integración del orquestador algorítmico, guiando así un diseño robusto y focalizado.

## 3. Tests
Las pruebas se consolidaron en la suite `InvalidGiftIdCalculatorTest`, segmentando la validación en niveles de aislamiento:
* **Pruebas de Frontera Geométrica (Caja Blanca)**: Validaciones individuales del predicado inyectando IDs directamente. Verifica falsos positivos (`101`) y asegura la detección precisa de simetrías (`55`, `123123`).
* **Prueba de Instanciación de Rango**: Aislamiento del *parsing* (extracción de límites y generación de flujos) en un rango único (`11-22`).
* **Prueba de Integración (Ejemplo Completo)**: Validación integral del flujo que evalúa el *parsing* múltiple, el filtrado de espacios en blanco `\s+` mediante expresiones regulares y la acumulación del sumatorio global sobre un gran volumen de datos.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA (Cohesión, Abstracción, Modularidad, Acoplamiento)**:
  * **Alta Cohesión**: La lógica topológica de la validación se confina estrictamente en la entidad `GiftId`, y la responsabilidad de delimitación y expansión numérica reside exclusivamente en la entidad `Range`.
  * **Bajo Acoplamiento**: Los flujos operan puramente con transferencias funcionales sin compartir referencias de memoria.
* **SOLID**:
  * **SRP (Responsabilidad Única)**: Cada clase posee una única razón para cambiar. `Range` actúa como factoría de flujos numéricos, `GiftId` evalúa su validez intrínseca, y `InvalidGiftIdCalculator` orquesta la tubería de cálculo sin conocer los pormenores de validación.
* **Clean Code**:
  * **SLAP (Single Level of Abstraction Principle)**: En `GiftId`, la validación principal (`isInvalidString`) delega en micro-métodos atómicos y descriptivos (`hasEvenLength`, `halvesAreEqual`, `midpoint`), desterrando la complejidad ciclomática de los condicionales.
  * **Diseño Defensivo**: Las clases utilitarias (`Main`, `InvalidGiftIdCalculator`) han sido declaradas como `final` para bloquear la herencia e incluyen constructores privados paramétricos que arrojan `UnsupportedOperationException`.
* **Paradigma Funcional y Rendimiento**: Se empleó intensivamente la API de Streams especializados (`LongStream`). La transición lógica mediante `flatMapToLong` y `mapToLong` erradica los bucles imperativos y suprime el impacto de rendimiento asociado a la instanciación de objetos envoltorio (*Autoboxing Overhead*).

## 5. Diagrama UML
![Diagrama UML Dia 02 - Parte A](images/day02.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada del componente ejecutable. Aísla completamente la infraestructura de lectura de archivos locales y elimina espacios en blanco innecesarios inyectando una cadena sanitizada.
* **InvalidGiftIdCalculator**: Clase utilitaria sin estado responsable de ensamblar el motor de procesamiento algebraico (Pipeline de Streams). Transforma un texto bruto en una proyección funcional de validación sumatoria global.
* **Range**: Entidad paramétrica (Value Object). Modelada mediante un record de Java. Oculta el parseo numérico de los límites del guion (-) y actúa como factoría de un flujo de enteros primitivos secuencial (LongStream.rangeClosed).
* **GiftId**: Entidad de dominio centralizada y auto-validada (Value Object inmutable). Encapsula el conocimiento estricto sobre las reglas morfológicas que determinan si una ID está corrupta (regla de longitud y regla de simetría), exponiendo un contrato booleano puro e independiente del volumen de datos exterior.