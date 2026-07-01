# Día 06: Trash Compactor (Parte A)

## 1. Descripción
El reto de la Parte A plantea el análisis computacional de una matriz de texto que contiene problemas aritméticos dispuestos horizontalmente. El objetivo es realizar un análisis léxico (*parsing*) para segmentar la matriz en bloques independientes mediante la identificación de columnas vacías, interpretar los operandos y el operador aritmético subyacente, y finalmente evaluar cada bloque para obtener la suma global de los resultados (Grand Total).

## 2. Metodología
Se ha seguido rigurosamente el ciclo de desarrollo **TDD (Test-Driven Development)**. Para garantizar una localización precisa de defectos (*Defect Localization*), las pruebas se dividieron en tres niveles de aislamiento: lógica pura de dominio, análisis léxico-espacial y orquestación del servicio. Esto permite iterar sobre las reglas de extracción visual sin comprometer las invariantes matemáticas.

## 3. Tests
La arquitectura de pruebas garantiza la estabilidad estructural del sistema separando las responsabilidades:
* **`ProblemTest`**: Aísla y verifica la invariante de dominio matemático. Valida que una entidad instanciada con una lista de números y un operador resuelva correctamente la ecuación (suma y multiplicación), independientemente de cómo se hayan extraído los datos.
* **`WorksheetParserTest`**: Aísla el motor de análisis léxico bidimensional. Verifica que el particionado basado en columnas vacías divida correctamente el texto en múltiples entidades `Problem` válidas.
* **`WorksheetEvaluatorTest`**: Prueba de integración que valida el flujo continuo: ingesta de texto bruto, delegación de *parsing*, iteración de la colección de problemas y acumulación final del resultado.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Principio de Responsabilidad Única (SRP)**: La arquitectura separa de manera estricta el análisis visual (`WorksheetParser`) de la lógica matemática orquestada (`WorksheetEvaluator`).
  * **Principio Abierto/Cerrado (OCP) y Patrón Strategy**: Se utilizó un enumerado funcional (`Operator`) que expone un método abstracto `apply`. Esto encapsula el algoritmo matemático de reducción por polimorfismo, permitiendo añadir futuros operadores sin alterar ni la entidad `Problem` ni el evaluador.
* **Clean Code**:
  * **Micro-métodos (SLAP)**: La alta complejidad ciclomática del *parsing* bidimensional se mitigó delegando responsabilidades a micro-funciones (`isStartOfBlock`, `findNextEmptyColumn`, `extractOperator`), manteniendo el flujo principal altamente descriptivo.
  * **Código Expresivo**: Se utilizó `Predicate.not(String::isEmpty)` como referencia a método, eliminando la verbosidad de las expresiones lambda en los filtros.
  * **Diseño Defensivo Espacial**: Los métodos `extractSafely` y `getSafeChar` evitan caídas del sistema por `IndexOutOfBoundsException` ante matrices de entrada asimétricas.
* **Paradigma Funcional y Streams**:
  * **Inmutabilidad**: La entidad `Problem` se modeló como un `record` de Java.
  * **Ausencia de Bucles de Control**: La identificación de columnas y la instanciación de bloques se realiza mediante tuberías de datos (`IntStream.range().filter().mapToObj()`), erradicando las variables mutables de estado e iteración imperativa.

## 5. Diagrama UML
![Diagrama UML Dia 06 - Parte A](images/day06.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Oculta la complejidad de la infraestructura de lectura del sistema de archivos e inyecta la cuadrícula de texto sin procesar en la capa de evaluación del dominio.
* **WorksheetEvaluator**: Orquestador funcional (Servicio de Dominio). Carece de estado y conecta el análisis visual con la ejecución matemática, sumando los resultados individuales en una tubería declarativa.
* **WorksheetParser**: Motor de análisis léxico y espacial. Aísla toda la lógica de segmentación bidimensional (identificación de columnas de separación y extracción segura de subcadenas), fabricando las entidades inmutables del sistema.
* **Operator**: Enumeración funcional que implementa el patrón Strategy. Asocia un símbolo textual a su implementación matemática pura, evaluando un flujo (Stream) de números a través de funciones de reducción geométrica (sumatorias o multiplicatorias).
* **Problem**: Entidad principal del dominio, modelada como record inmutable. Almacena de forma segura los operandos y el operador de un bloque, sirviendo como proxy para la evaluación del resultado algebraico de dicho problema aislado.