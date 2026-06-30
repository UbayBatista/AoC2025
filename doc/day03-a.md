# Día 03: Lobby (Parte A)

## 1. Descripción
El reto de la Parte A nos sitúa ante el problema de energizar una escalera mecánica mediante bancos de baterías. Cada banco está representado por una secuencia de dígitos numéricos donde cada dígito representa el voltaje de una batería individual. La regla de negocio exige seleccionar exactamente dos baterías de cada banco, manteniendo rigurosamente su orden de aparición original (índices $i < j$), de modo que el número de dos dígitos resultante sea el máximo posible. El objetivo final del dominio es calcular la sumatoria de estos voltajes máximos de todos los bancos provistos en la entrada.

## 2. Metodología
Se aplicó estrictamente el ciclo de desarrollo guiado por pruebas **TDD (Test-Driven Development)**. En la fase RED, se diseñó una suite de pruebas automatizadas mediante JUnit y AssertJ que incluía pruebas parametrizadas con los casos de ejemplo para asegurar la cobertura de diversos escenarios numéricos. Asimismo, se integraron pruebas de caja negra y de frontera para validar que las entradas inválidas lancen de forma determinista la excepción correspondiente, garantizando una estrategia sólida de localización de defectos (*Defect Localization*).

## 3. Tests
Las pruebas se consolidaron en `JoltageCalculatorTest`, estructuradas de la siguiente manera:
* **Pruebas Parametrizadas (Regla de Negocio)**: Uso de `@CsvSource` para inyectar múltiples escenarios de cadenas y sus resultados esperados. Esto valida exhaustivamente la correcta combinación de índices ($i < j$) sin duplicar código de aserción.
* **Prueba de Integración**: Verifica que el método `calculateTotalJoltage` acumule correctamente la sumatoria total al procesar una lista de múltiples bancos de baterías.
* **Prueba de Diseño Defensivo (Frontera)**: Verifica que el sistema se proteja ante datos anómalos o incompletos (cadenas de longitud inferior a 2), asegurando el lanzamiento explícito de `IllegalArgumentException`.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Alta Cohesión y Bajo Acoplamiento**: La lógica pura de la resolución matemática combinatoria se confina dentro de la clase `JoltageCalculator`, abstrayéndola completamente de cualquier infraestructura de lectura de archivos. Al usar funciones puras sin estado, el acoplamiento con la clase `Main` es mínimo.
  * **SRP (Responsabilidad Única)**: `Main` se encarga exclusivamente de la entrada/salida, mientras que `JoltageCalculator` asume de forma única el cálculo algorítmico.
* **Clean Code**:
  * **Micro-métodos (SLAP)**: Se estructuró el componente de dominio delegando responsabilidades a micro-métodos (`validateBank` y `computeJoltageForPair`), permitiendo que el algoritmo principal opere en un único nivel de abstracción y sea altamente legible.
  * **Diseño Defensivo**: Ambas clases aplican el principio de fallo rápido (*Fail-Fast*) mediante constructores privados que lanzan `UnsupportedOperationException`, bloqueando la instanciación accidental de clases utilitarias.
* **Paradigma Funcional y Rendimiento**:
  * **Aplanamiento Bidimensional (flatMap)**: Se sustituyó por completo la iteración imperativa tradicional de bucles anidados por un enfoque puramente declarativo. Se empleó `IntStream.range` combinado con un mapeo plano (`flatMap`) para generar y proyectar el espacio de combinaciones válidas de forma segura.
  * **Resolución de Mónadas**: El valor máximo se extrae gestionando de forma segura la mónada `OptionalInt` devuelta por el operador terminal `.max()`, utilizando `.orElseThrow()` para garantizar la integridad determinista del sistema frente a fallos imprevistos de cálculo.

## 5. Diagrama UML
![Diagrama UML Dia 03 - Parte A](images/day03-a.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Responsable de la capa de infraestructura, realiza la lectura del archivo de datos bruto y transfiere el listado de cadenas resultantes hacia la capa de dominio.
* **JoltageCalculator**: Clase utilitaria y pura (sin estado mutable). Contiene la lógica algorítmica y matemática del dominio. Encargada de validar las restricciones morfológicas de los bancos de baterías y de aplicar la combinatoria funcional (mediante Streams) para hallar los voltajes máximos de manera declarativa.