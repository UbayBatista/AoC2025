# Advent of Code 2025 - Ingeniería del Software II

## 1. Descripción del Proyecto
Este repositorio contiene la resolución de los retos del Advent of Code 2025 como parte de la evaluación de la asignatura Ingeniería del Software 2 (IS2). El objetivo principal no es únicamente la resolución algorítmica de los problemas, sino la aplicación rigurosa de metodologías de desarrollo profesional, diseño arquitectónico y prácticas de código limpio.

## 2. Tecnologías y Metodología
* **Lenguaje:** Java 25 (Paradigma Orientado a Objetos y Funcional).
* **Gestor de dependencias:** Maven.
* **Pruebas (TDD):** JUnit 6 y AssertJ para garantizar la robustez mediante el ciclo Red/Green/Refactor.
* **Control de Versiones:** Uso de Conventional Commits reflejando iteraciones atómicas de desarrollo.

## 3. Fundamentos, Principios y Prácticas
El desarrollo del proyecto se rige por los siguientes estándares teóricos y técnicos:

* **Fundamentos (CAMA):**
  * **Alta Cohesión:** Cada clase tiene una única responsabilidad, concentrando la lógica de dominio en sus entidades correspondientes.
  * **Bajo Acoplamiento:** Separación estricta entre la infraestructura (I/O) y el dominio, utilizando la inyección de dependencias para desacoplar el motor de resolución de la fuente de datos.
  * **Modularidad:** Estructuración del sistema en paquetes funcionales (Día/Reto por Día/Reto) que facilitan la navegabilidad y el mantenimiento.
  * **Abstracción Adecuada:** Aplicación del principio de **SLAP** (*Single Level of Abstraction Principle*), descomponiendo algoritmos complejos en micro-métodos que mantienen una coherencia léxica y de nivel de detalle.

* **Principios de Diseño:**
  * **SOLID:** Aplicación constante, con énfasis en **SRP** (clases pequeñas) y **OCP** (extensión de funcionalidad mediante nuevas clases sin modificar las existentes).
  * **KISS, YAGNI y DRY:** Eliminación sistemática de código duplicado, rechazo a funcionalidades innecesarias y búsqueda de la simplicidad técnica.

* **Clean Code y Paradigma Funcional:**
  * **Expresividad:** Uso de *Good Naming* y código auto-documentado para eliminar la necesidad de comentarios redundantes.
  * **Encadenamiento de Métodos:** Aplicación de *Method Chaining* para crear flujos declarativos fluidos y legibles.
  * **Inmutabilidad:** Uso extensivo de `records` y colecciones inmutables para garantizar la predictibilidad del estado.
  * **Seguridad de Tipos y Nulos:** Sustitución de verificaciones imperativas por mónadas (`Optional`) y APIs funcionales para eliminar el riesgo de `NullPointerException`.
  * **Declaratividad:** Manipulación de colecciones mediante `Streams` para elevar el nivel de abstracción.

* **Estrategia de Desarrollo y Calidad:**
  * **TDD (Test-Driven Development):** Ciclo estricto *Red-Green-Refactor* para asegurar que cada funcionalidad esté respaldada por una especificación ejecutable.
  * **Convención de Pruebas:** Todos los métodos de test siguen estrictamente la convención:
    * **Formato:** `snake_case` para mejorar la legibilidad.
    * **Prefijo:** Deben comenzar siempre con `should_` (ej. `should_calculate_minimum_presses_for_machine`).
  * **Documentación Técnica:** Generación de diagramas UML y descripciones arquitectónicas por cada reto para asegurar la trazabilidad.

## 4. Registro de Retos y Decisiones Técnicas

A continuación, se detalla el progreso diario, destacando los patrones y principios clave aplicados en cada solución. El análisis profundo y los diagramas UML se encuentran en la documentación específica de cada día en la carpeta `doc/`.

|  Día  | Reto        | Patrones de Diseño | Principios y Técnicas Destacadas | Documentación |
|:-----:|:------------| :--- | :--- | :--- |
| 01-A  | Secret Entrance (A) | Factory Method | SRP, DIP, DRY | [day01-a.md](doc/day01-a.md) |
| 01-B  | Secret Entrance (B) | Factory Method | SRP | [day01-b.md](doc/day01-b.md) |
| 02-A  | Gift Shop (A) | - | SRP, KISS, YAGNI | [day02-a.md](doc/day02-a.md) |
| 02-B  | Gift Shop (B) | - | SRP, OCP | [day02-b.md](doc/day02-b.md) |
| 03-A  | Lobby (A) | - | SRP, KISS | [day03-a.md](doc/day03-a.md) |
| 03-B  | Lobby (B)  | Facade | DDD, SRP | [day03-b.md](doc/day03-b.md) |
| 04-A  | Printing Department (A) | Factory Method | SRP, Ley de Demeter | [day04-a.md](doc/day04-a.md) |
| 04-B  | Printing Department (B) | Factory Method | OCP, DRY | [day04-b.md](doc/day04-b.md) |
| 05-A  | Cafeteria (A) | - | SRP, KISS | [day05-a.md](doc/day05-a.md) |
| 05-B  | Cafeteria (B) | Tell don't ask | SRP, OCP | [day05-b.md](doc/day05-b.md) |
| 06-A  | Trash Compactor (A) | Patrón Strategy (Enum) | SRP, OCP | [day06-a.md](doc/day06-a.md) |
| 06-B  | Trash Compactor (B) | Patrón Strategy (Enum) | OCP, DRY | [day06-b.md](doc/day06-b.md) |
| 07-A  | Laboratories (A) | Factory Method | SRP, COI | [day07-a.md](doc/day07-a.md) |
| 07-B  | Laboratories (B) | - | SRP, SLAP | [day07-b.md](doc/day07-b.md) |
| 08-A  | Playground (A) | - | SRP, SLAP, DSU (Union-Find) | [day08-a.md](doc/day08-a.md) |
| 08-B  | Playground (B) | - | OCP, Algoritmo de Kruskal | [day08-b.md](doc/day08-b.md) |
| 09-A  | Movie Theater (A) | Tell Don't Ask | SRP | [day09-a.md](doc/day09-a.md) |
| 09-B  | Movie Theater (B) | - | SRP, OCP, SLAP, Algoritmo de Ray-Casting | [day09-b.md](doc/day09-b.md) |
| 10-A  | Factory (A) | Factory Method | SRP, YAGNI, Gauss-Jordan GF(2) | [day10-a.md](doc/day10-a.md) |
| 10-B  | Factory (B) | - | OCP, SLAP, ILP, Pivoteo Parcial | [day10-b.md](doc/day10-b.md) |
| 11-A  | Reactor (A) | - | SRP, Ley de Deméter, DFS Declarativo | [day11-a.md](doc/day11-a.md) |
| 11-B  | Reactor (B) | Method Object | DRY, SLAP, Programación Dinámica (Memoización) | [day11-b.md](doc/day11-b.md) |
| 12-A  | Christmas Tree Farm (A) | Factory Method | SRP, Programación Dinámica (Memoización), Largest-First Pruning | [day12-a.md](doc/day12-a.md) |

## 5. Instrucciones de Ejecución e Integración
El proyecto sigue la estructura de directorios estándar de Maven.

Para ejecutar la batería de pruebas automatizadas, utiliza el siguiente comando en la raíz del proyecto:
```bash
mvn clean test
```