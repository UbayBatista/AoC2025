# Día 01: Secret Entrance (Parte B)

## 1. Descripción
La Parte B evoluciona la regla de conteo del reto anterior. Ahora, el sistema debe registrar el número de veces que el dial apunta al valor 0 no solo al finalizar una rotación, sino también durante el tránsito de la misma (pasos intermedios).

## 2. Metodología
Se aplicó **TDD** expandiendo la suite de pruebas existente. Se ampliaron los tests de `DialStateTest` para validar específicamente:
- Movimientos sin cruce de cero.
- Cruces de cero exactos.
- Grandes rotaciones que implican múltiples vueltas completas.

## 3. Fundamentos y Principios
* **SRP (Responsabilidad Única)**: `DialState` centraliza la lógica de cálculo incremental. El uso de `IntStream` permite realizar el conteo de pasos intermedios de forma declarativa sin romper la inmutabilidad.
* **Paradigma Funcional**: La transición de estado se mantiene pura; `next(int step)` devuelve una nueva instancia con el estado calculado, eliminando efectos secundarios.
* **Clean Code**: Se eliminaron dependencias de paquetes innecesarias (`imports`) para asegurar una estricta separación de los contextos (Parte A vs Parte B) y evitar colisiones de clases durante la compilación.