# Día 01: Secret Entrance (Parte B)

## 1. Descripción
La Parte B evoluciona la regla de conteo del reto anterior. Ahora, el sistema debe registrar el número de veces que el dial apunta al valor 0 no solo al finalizar una rotación, sino también durante el tránsito de la misma (pasos intermedios).

## 2. Metodología
Se aplicó estrictamente el ciclo **TDD (Test-Driven Development)** expandiendo la suite de pruebas existente. Aprovechando el diseño modular previo, se evitó la regresión de los componentes de entrada/salida y orquestación, focalizando el ciclo Red-Green-Refactor exclusivamente en la invariante matemática de la entidad de dominio.

## 3. Tests
Las pruebas se ampliaron para validar la casuística de los pasos intermedios, garantizando la localización de defectos (*Defect Localization*):
* **`RotationParserTest`**: Mantiene la validación del análisis léxico, asegurando que las directivas se sigan traduciendo correctamente a magnitudes enteras con signo.
* **`DialStateTest`**: Se enriqueció con nuevas aserciones para validar lógicas de tránsito:
    * Movimientos que no cruzan el cero (`should_not_count_zero_passes_if_dial_does_not_cross_or_reach_zero`).
    * Movimientos que terminan exactamente en el cero (`should_count_one_zero_pass_if_dial_reaches_zero_exactly`).
    * Rotaciones masivas (ej. `10000` pasos) que implican dar múltiples vueltas completas al dial (`should_count_multiple_zero_passes_for_large_rotations`).
* **`SafeDialTest`**: Prueba de integración que verifica la correcta acumulación funcional de los cruces por cero intermedios, validando contra el nuevo resultado esperado del dominio.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
    * **OCP (Principio Abierto/Cerrado)**: La arquitectura demostró su resiliencia. `SafeDial` y `RotationParser` permanecieron cerrados a la modificación. La nueva regla de negocio se satisfizo extendiendo únicamente el comportamiento interno de `DialState`.
    * **SRP (Responsabilidad Única)**: `DialState` sigue siendo el único responsable de la lógica del dial, encapsulando la complejidad de simular los pasos espaciales intermedios.
* **Clean Code**:
    * **SLAP (Single Level of Abstraction Principle)**: La transición de estado (`next`) delega en dos micro-métodos expresivos (`calculateNewPosition` y `countZerosDuring`), permitiendo una lectura fluida y humana de la regla de negocio.
    * **Erradicación del Control de Flujo Imperativo**: Se eliminó la necesidad de bucles tradicionales `for` e `if` condicionales.
* **Paradigma Funcional e Inmutabilidad**: La entidad `DialState` mantiene su naturaleza de `record` inmutable. Para calcular los pasos intermedios sin variables mutables, se empleó un enfoque declarativo puro: `IntStream.rangeClosed` genera el flujo de pasos, `map` proyecta las coordenadas topológicas mediante aritmética modular, `filter` aísla los cruces por cero, y `count` resuelve el agregado numérico final.

## 5. Diagrama UML
![Diagrama UML Dia 01 - Parte B](images/day01.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Es responsable exclusivamente de la capa de infraestructura (lectura del sistema de archivos I/O) y de orquestar la inyección de la entrada sin procesar hacia la capa de dominio.
* **SafeDial**: Servicio principal de dominio. Actúa como orquestador de alto nivel aplicando un flujo declarativo (Pipeline de Streams) para transformar el listado de cadenas de entrada en el conteo total acumulado.
* **RotationParser**: Clase utilitaria y pura (sin estado). Su única responsabilidad es el análisis léxico: transformar cadenas de texto (ej. "L50") en magnitudes direccionales (-50).
* **DialState**: Entidad central (Value Object) del modelo de dominio. Modelada como un record inmutable. Evalúa los pasos intermedios de cada rotación utilizando matemática pura y generación de flujos (Streams), devolviendo siempre una nueva instancia con la posición normalizada y el conteo de ceros acumulado sin efectos secundarios.