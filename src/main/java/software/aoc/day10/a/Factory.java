package software.aoc.day10.a;

import java.util.List;

public record Factory(List<Machine> machines) {

    public Factory {
        machines = List.copyOf(machines);
    }

    public int calculateMinimumTotalPresses() {
        return machines.stream()
                .map(EquationSystem::from)
                .mapToInt(GaussianSolver::solve)
                .sum();
    }
}
