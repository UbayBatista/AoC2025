# Día 02: Gift Shop (Parte B)

## 1. Descripción
La Parte B introduce una evolución en la regla de negocio del dominio: el criterio de invalidez de un identificador de producto se expande. Mientras que originalmente se buscaba una repetición doble exacta, ahora un ID se considera inválido si está formado por un patrón numérico que se repite consecutivamente al menos dos veces (pudiendo ser tres, cuatro, cinco o más repeticiones, de forma que el patrón cubra la totalidad de la cadena). El objetivo se mantiene en calcular la sumatoria de todos los identificadores que cumplen esta nueva condición.

## 2. Metodología
Se continuó con el ciclo **TDD (Test-Driven Development)** expandiendo la suite de pruebas del dominio. Aprovechando el fuerte desacoplamiento de la arquitectura, se previnieron regresiones en la lógica de *parsing* y orquestación, centrando el esfuerzo de refactorización exclusivamente en la invariante matemática de la entidad de dominio.

## 3. Tests
La suite `InvalidGiftIdCalculatorTest` se enriqueció para consolidar la nueva definición de invalidez geométrica, garantizando una precisa localización de defectos:
* **Pruebas de Patrón Múltiple**: Se añadieron aserciones para validar secuencias de repetición impar (`111`), secuencias largas (`11111111`) y repeticiones de bloques numéricos (`2121212121`).
* **Pruebas de Falsos Positivos**: Se garantizó que subcadenas simétricas que no cubren la totalidad del ID (`1122`) se sigan identificando como válidas.
* **Prueba de Integración**: Se actualizó la aserción final del sumatorio de rangos (`4174379265L`) para reflejar la expansión del conjunto de identificadores inválidos.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **OCP (Principio Abierto/Cerrado)**: La tubería de cálculo (`InvalidGiftIdCalculator`) y el factorizador de flujos (`Range`) se mantuvieron cerrados para su modificación. La nueva funcionalidad se adaptó alterando exclusivamente la lógica interna de la entidad `GiftId`, probando la resiliencia del diseño.
  * **SRP (Responsabilidad Única)**: Se mantuvo la separación conceptual entre la orquestación del flujo de datos y la evaluación combinatoria del identificador.
* **Clean Code**:
  * **Micro-métodos (SLAP)**: La complejidad algorítmica se segmentó en funciones puras y atómicas (`isExactDivisor`, `isRepeatedPattern`), permitiendo que el método principal se lea como una especificación de reglas funcionales.
* **Paradigma Funcional y Rendimiento**:
  * **Evaluación Perezosa (Lazy Evaluation)**: Se resolvió el problema combinatorio de forma puramente declarativa mediante `IntStream.rangeClosed`. El uso del operador de cortocircuito `anyMatch` detiene la evaluación en el momento en que se encuentra el primer patrón válido, optimizando los ciclos de CPU.
  * **Poda Matemática**: El predicado previo `filter(subLen -> isExactDivisor(...))` actúa como un heurístico de rendimiento, evitando costosas extracciones de subcadenas y comparaciones de texto (`String.repeat`) para longitudes que, por aritmética básica, jamás podrían cubrir la cadena completa.

## 5. Diagrama UML
![Diagrama UML Dia 02 - Parte B](/images/day02.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada del componente ejecutable. Aísla completamente la infraestructura de lectura de archivos locales y elimina espacios en blanco innecesarios inyectando una cadena sanitizada.
* **InvalidGiftIdCalculator**: Clase utilitaria sin estado responsable de ensamblar el motor de procesamiento algebraico (Pipeline de Streams). Transforma un texto bruto en una proyección funcional de validación sumatoria global.
* **Range**: Entidad paramétrica (Value Object). Modelada mediante un record de Java. Oculta el parseo numérico de los límites del guion (-) y actúa como factoría de un flujo de enteros primitivos secuencial (LongStream.rangeClosed).
* **GiftId**: Entidad de dominio centralizada y auto-validada (Value Object inmutable). Encapsula la lógica combinatoria matemática para identificar patrones simétricos subyacentes de tamaño variable, exponiendo un contrato booleano puro.