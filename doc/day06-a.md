# Día 06: Trash Compactor (Parte A)

## 1. Descripción
El reto de la Parte A plantea el análisis computacional de una matriz de texto que contiene problemas aritméticos dispuestos horizontalmente. El objetivo es realizar un análisis léxico (*parsing*) para segmentar la matriz en bloques independientes mediante la identificación de columnas vacías, interpretar los operandos y el operador aritmético subyacente, y finalmente evaluar cada bloque para obtener la suma global de los resultados (Grand Total).

## 2. Metodología
Se ha seguido rigurosamente el ciclo de desarrollo **TDD (Test-Driven Development)**, dividiendo las pruebas en tres niveles de aislamiento para garantizar la localización de defectos: lógica de dominio (`ProblemTest`), análisis léxico (`WorksheetParserTest`) y orquestación del servicio (`WorksheetEvaluatorTest`).

## 3. Fundamentos y Principios
* **CAMA**:
    * **Alta Cohesión y Bajo Acoplamiento**: La arquitectura separa de manera estricta las responsabilidades. El analizador (`WorksheetParser`) desconoce la lógica de evaluación, limitándose a instanciar entidades del dominio. El orquestador (`WorksheetEvaluator`) ignora los detalles del análisis léxico, delegando el flujo y centrándose en la suma total.
    * **Código Expresivo (Good Naming)**: Se han utilizado variables descriptivas y precisas respecto a su nivel de abstracción. Por ejemplo, el uso de `rawInput` permite distinguir claramente un modelo de datos externo no procesado de una entidad de dominio estructurada.
* **SOLID**:
    * **SRP (Principio de Responsabilidad Única)**: Aplicado mediante la extracción exhaustiva de *Micro-métodos* en el componente de análisis léxico. Cada método privado (`extractBlockLines`, `extractNumbers`, `extractOperator`) se encarga de una única operación de transformación, evitando la complejidad ciclomática y garantizando que las funciones sean pequeñas y enfocadas.
    * **OCP (Principio Abierto/Cerrado)**: El diseño es abierto a la extensión pero cerrado a la modificación. Al utilizar una enumeración funcional (`Operator`) que encapsula la lógica matemática mediante polimorfismo, es posible introducir nuevas operaciones aritméticas en el futuro sin alterar el código de la entidad `Problem` ni la lógica de evaluación.

## 4. Paradigma Funcional y Clean Code
* **Inmutabilidad**: Las entidades del dominio se han modelado mediante la estructura `record` (`Problem`), garantizando que los objetos generados sean inmutables y carezcan de efectos secundarios o estados intermedios mutables.
* **Programación Declarativa (Streams)**: Se han reemplazado las estructuras iterativas imperativas por la API de `Streams`. El análisis léxico proyecta índices mediante `IntStream.range().filter().mapToObj()`, construyendo un *Pipeline* de datos eficiente y semánticamente claro. Adicionalmente, se emplea *Method Reference* (p. ej., `Predicate.not(String::isEmpty)`) para reducir la verbosidad de las funciones lambda.