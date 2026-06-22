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

| Día | Reto | Patrones de Diseño | Principios y Técnicas Destacadas | Documentación |
| :---: | :--- | :--- | :--- | :--- |
| **01** | *Pendiente* | - | - | - |

## 5. Instrucciones de Ejecución e Integración
El proyecto sigue la estructura de directorios estándar de Maven.

Para ejecutar la batería de pruebas automatizadas, utiliza el siguiente comando en la raíz del proyecto:
```bash
mvn clean test
```