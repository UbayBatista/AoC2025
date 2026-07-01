# Día 04: Printing Department (Parte A)

## 1. Descripción
El reto de la Parte A consiste en identificar, dentro de un almacén bidimensional, cuántos rollos de papel (`@`) resultan "accesibles" para las carretillas elevadoras. La regla de negocio determina que un rollo es accesible si tiene estrictamente menos de cuatro rollos en sus ocho posiciones adyacentes. El objetivo del dominio es procesar la cuadrícula y devolver este recuento total.

## 2. Metodología
Se aplicó estrictamente el ciclo **TDD (Test-Driven Development)**. En la fase RED, se diseñó una suite de pruebas con JUnit y AssertJ que incluyó el escenario del enunciado base y aserciones orientadas a límites de frontera (como un rollo completamente aislado o uno bloqueado). En la fase GREEN, se implementó la solución adoptando desde el principio un enfoque puramente funcional y topológico, proyectando la cuadrícula sobre un conjunto matemático de coordenadas para evaluar las adyacencias.

## 3. Tests
La suite `PaperGridTest` asegura la correctitud algorítmica mediante tres niveles de aislamiento (*Defect Localization*):
* **Prueba de Frontera - Aislamiento Absoluto**: Se evalúa un rollo único sin vecinos (`should_identify_isolated_roll_as_accessible`), garantizando que la ausencia de adyacencias se interprete matemáticamente como `< 4`.
* **Prueba de Frontera - Bloqueo Absoluto**: Se evalúa un rollo completamente rodeado en una matriz de $3 \times 3$ (`should_identify_surrounded_roll_as_inaccessible`), validando el límite máximo de saturación de la regla de negocio.
* **Prueba de Integración Topológica**: Comprueba que el Factory Method (`from`) instancie correctamente el estado desde cadenas de texto y el servicio resuelva las múltiples condiciones parciales del almacén global (ejemplo del enunciado).

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
  * **Ley de Deméter (LoD) y Bajo Acoplamiento**: La entidad `Coordinate` calcula sus adyacencias internamente (`neighbors()`), impidiendo que `PaperGrid` deba interrogar sus ejes $x$ e $y$ para realizar cálculos espaciales externos.
  * **SRP (Responsabilidad Única)**: Existe una separación estricta; `Main` coordina la infraestructura de entrada/salida (I/O), mientras que `PaperGrid` evalúa exclusivamente las reglas de distribución del dominio.
* **Clean Code**:
  * **Factory Method**: Se empleó el patrón de creación `PaperGrid.from(List<String>)` para aislar y abstraer la complejidad del parseo espacial, dejando los constructores estándar cerrados para uso interno.
  * **SLAP (Single Level of Abstraction Principle)**: El dominio operativo delega la regla booleana en el micro-método `isAccessible`, manteniendo el método coordinador `countAccessibleRolls` en un nivel de lectura declarativa fluida.
* **Paradigma Funcional y Optimización**:
  * **Colecciones Inmutables**: El uso de `Set.copyOf(rolls)` previene cualquier mutación temporal o externa.
  * **Mapeo Espacial Constante**: Se transformó la representación tabular tradicional (arrays 2D) en un `Set` matemático. Esto optimiza la búsqueda de los vecinos (`rolls::contains`) a una complejidad temporal $O(1)$.
  * **Proyección Plana Bidimensional**: Se utilizó `IntStream.range` combinado con `flatMap` para iterar el espacio de forma inmutable, filtrando y transformando solo los caracteres válidos en coordenadas instanciadas.

## 5. Diagrama UML
![Diagrama UML Dia 04 - Parte A](images/day04.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada de la aplicación. Gestiona estrictamente la capa de infraestructura interactuando con el sistema de archivos, inyectando las líneas de texto bruto al dominio.
* **PaperGrid**: Entidad orquestadora del almacén topológico. Utiliza un Factory Method estático para convertir un listado de cadenas en un plano espacial inmutable (Set), encargándose de aplicar la regla de negocio para el filtrado y recuento de los rollos accesibles.
* **Coordinate**: Entidad fundamental (Value Object) modelada como record inmutable. Encapsula su propia posición espacial y es autosuficiente para proyectar topológicamente las ocho coordenadas adyacentes a sí misma mediante la generación de un flujo (Stream).