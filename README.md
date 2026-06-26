# Advent of Code 2025 - Ingeniería del Software II

## 1. Descripción del Proyecto
Este repositorio contiene la resolución de los retos del Advent of Code 2025 como parte de la evaluación de la asignatura Ingeniería del Software 2 (IS2). El objetivo principal no es únicamente la resolución algorítmica de los problemas, sino la aplicación rigurosa de metodologías de desarrollo profesional, diseño arquitectónico y prácticas de código limpio.

## 2. Tecnologías y Metodología
* **Lenguaje:** Java 25 (Paradigma Orientado a Objetos y Funcional).
* **Gestor de dependencias:** Maven.
* **Pruebas (TDD):** JUnit 6 y AssertJ para garantizar la robustez mediante el ciclo Red/Green/Refactor.
* **Control de Versiones:** Uso de Conventional Commits reflejando iteraciones atómicas de desarrollo.

## 3. Fundamentos, Principios y Prácticas
El desarrollo del proyecto se rige por los siguientes estándares teóricos:
* **Fundamentos (CAMA):** Mantenimiento estricto de alta Cohesión, Abstracción adecuada, Modularidad y bajo Acoplamiento.
* **Principios de Diseño:** 
    * **SOLID:** Aplicación constante, destacando la Responsabilidad Única (SRP) y la Inversión de Dependencias (DIP) mediante interfaces.
    * **KISS, YAGNI y DRY:** Evitando el código "zombie", la duplicidad y las abstracciones innecesarias.
* **Clean Code y Paradigma Funcional:** Uso de *Good Naming*, clases inmutables, mónadas (`Optional`) para evitar nulos y manipulación de colecciones mediante API de `Streams`.

## 4. Registro de Retos y Decisiones Técnicas

A continuación, se detalla el progreso diario, destacando los patrones y principios clave aplicados en cada solución. El análisis profundo y los diagramas UML se encuentran en la documentación específica de cada día en la carpeta `doc/`.

|   Día    | Reto        | Patrones de Diseño | Principios y Técnicas Destacadas | Documentación |
|:--------:|:------------| :--- | :--- | :--- |
|   01-A   | Secret Entrance (A) | Factory Method | SRP, DIP, DRY | [day01-a.md](doc/day01-a.md) |
|   01-B   | Secret Entrance (B) | Factory Method | SRP, Streams API | [day01-b.md](doc/day01-b.md) |
|   02-A   | Gift Shop (A) | - | SRP, KISS, YAGNI | [day02-a.md](doc/day02-a.md) |
|   02-B   | Gift Shop (B) | - | SRP, OCP | [day02-b.md](doc/day02-b.md) |
|   03-A   | Lobby (A) | - | SRP, KISS | [day03-a.md](doc/day03-a.md) |
|   03-B   | Lobby (B)  | Facade | DDD, SRP | [day03-b.md](doc/day03-b.md) |
|   04-A   | Printing Department (A) | Factory Method | SRP, Ley de Demeter | [day04-a.md](doc/day04-a.md) |
|   04-B   | Printing Department (B) | Factory Method | OCP, DRY | [day04-b.md](doc/day04-b.md) |
|   05-A   | Cafeteria (A) | - | SRP, KISS | [day05-a.md](doc/day05-a.md) |
|   05-B   | Cafeteria (B) | Tell don't ask | SRP, OCP | [day05-b.md](doc/day05-b.md) |
|   06-A   | Trash Compactor (A) | Patrón Strategy (Enum) | SRP, OCP | [day06-a.md](doc/day06-a.md) |
| **06-B** | *Pendiente* | - | - | - |

## 5. Instrucciones de Ejecución e Integración
El proyecto sigue la estructura de directorios estándar de Maven.

Para ejecutar la batería de pruebas automatizadas, utiliza el siguiente comando en la raíz del proyecto:
```bash
mvn clean test
```