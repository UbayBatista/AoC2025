# Día 12: Christmas Tree Farm (Parte A)

## 1. Descripción
El reto de la Parte A presenta un problema clásico de optimización combinatoria conocido como *Bin Packing 2D* (Empaquetado Bidimensional), categorizado como NP-Completo. El objetivo es determinar si un conjunto de formas geométricas irregulares (los regalos) puede confinarse dentro de los límites estrictos de una cuadrícula bidimensional (la región bajo el árbol), permitiendo rotaciones y reflexiones, pero prohibiendo estrictamente el solapamiento.

Dada la naturaleza del problema, una implementación ingenua de búsqueda en profundidad (DFS) derivaría en una explosión combinatoria con una complejidad temporal inasumible de $O(N!)$. Por consiguiente, el diseño del sistema requiere una arquitectura algorítmica avanzada que combine Programación Dinámica (Memoización), heurísticas de poda geométrica y un control estricto del perfil de memoria en la Máquina Virtual de Java (Evitación del *Garbage Collector Overhead*).

## 2. Metodología
Se ha implementado un motor de **Backtracking (Búsqueda Exhaustiva con Poda)** para explorar el espacio de soluciones. Dada la naturaleza NP-completa del problema de empaquetado, se introdujeron heurísticas de ordenación (*Sort by Ascending Size*) para podar el árbol de búsqueda de forma eficiente. El sistema garantiza la unicidad de las piezas mediante la normalización de coordenadas tras cada transformación geométrica.

## 3. Tests
La suite `TreeRegionPackerTest` y `PresentShapePermutatorTest` aseguran que tanto el motor geométrico como el de empaquetado funcionen bajo restricciones estrictas:
* **`PresentShapePermutatorTest`**: Valida que la generación de simetrías produzca exactamente las 8 orientaciones posibles (rotaciones + reflexiones) y que todas se normalicen al origen $(0,0)$.
* **`TreeRegionPackerTest`**: Valida casos de éxito en empaquetado de alta densidad y casos de falla por sobrepoblación (sobrepasar el área total de la región), asegurando que el *backtracker* retroceda correctamente cuando una disposición es inviable.

## 4. Fundamentos, Principios, Patrones y Técnicas
* **CAMA y SOLID**:
    * **Single Level of Abstraction (SLAP)**: La clase `TreeRegionPacker` descompone el empaquetado en pasos lógicos: filtrado de área, ordenación heurística y exploración recursiva.
    * **Tell, Don't Ask**: La lógica de colisión reside dentro de `WorkspaceGrid`. El motor de búsqueda no consulta si un bloque es ocupado, sino que ordena al grid colocarlo (`grid.place`).
* **Clean Code**:
    * **Encapsulamiento de Estado**: `WorkspaceGrid` utiliza un arreglo primitivo `boolean[][]` para maximizar la velocidad de acceso, pero oculta completamente esta estructura mediante una interfaz limpia basada en coordenadas (`Point`).
* **Paradigma Funcional y Algoritmia**:
    * **Programación Dinámica** (Pre-cálculo de Estados): Se utiliza buildPlacementCache para pre-computar todas las variantes geométricas (8 orientaciones) de cada forma, transformando una operación costosa en una consulta $O(1)$ durante el backtracking.
    * **Normalización de Formas**: Para evitar el almacenamiento redundante, se normaliza cada pieza desplazando sus coordenadas mínimas al origen $(0,0)$.
    * **Algoritmo de Backtracking (Recursión)**: Se evita la mutabilidad excesiva utilizando el patrón de "colocar-explorar-deshacer". El grid se revierte tras cada intento fallido, permitiendo explorar todas las combinaciones con un único espacio de memoria en $O(1)$ por intento.
    * **Heurística de Poda**: El ordenamiento de las piezas por tamaño ascendente es una optimización de "fail-fast" que descarta configuraciones inválidas en niveles superiores del árbol de recursión.

## 5. Diagrama UML
![Diagrama UML Dia 12 - Parte A](images/day12.png)

## 6. Descripción de las Clases
* **Main**: Punto de entrada. Coordina la carga de los datos crudos y delega la ejecución de la lógica de negocio al ChristmasTreeFarmSolver.
* **ChristmasTreeFarmSolver**: Clase de servicio que orquesta la resolución del problema: inicia el análisis del cultivo y cuenta cuántas regiones cumplen con los requisitos de empaquetado.
* **ChristmasTreeFarm**: Entidad (record) que actúa como contenedor principal de todos los datos parseados. Incluye la lógica de carga (from) que divide el archivo en bloques (formas vs. regiones).
* **TreeRegion**: Entidad que define una región de cultivo, sus dimensiones y, crucialmente, el mapa de requisitos de formas (requirements).
* **TreeRegionPacker**: Motor principal de backtracking. Es la pieza central que intenta encajar las formas en el espacio disponible utilizando la clase interna WorkspaceGrid.
* **WorkspaceGrid**: Estructura de datos interna (tablero) que realiza las operaciones de bajo nivel de colocación y eliminación de piezas (Backtracking puro).
* **PresentShapePermutator**: Servicio utilitario que genera todas las orientaciones geométricas posibles de una pieza (8 orientaciones: 4 rotaciones y sus respectivas reflexiones).
* **PresentShape**: Objeto de dominio que encapsula la forma de una pieza mediante un conjunto de Point. Contiene la lógica para normalizar las piezas y asegurar que se evalúen desde el origen $(0,0)$.
* **Point**: Objeto geométrico básico (Value Object) que permite realizar transformaciones espaciales (rotación, traslación, reflexión) de forma inmutable.