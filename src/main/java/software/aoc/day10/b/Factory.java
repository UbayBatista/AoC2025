package software.aoc.day10.b;

import java.util.List;

public record Factory(List<Machine> machines) {
    public Factory {
        machines = List.copyOf(machines);
    }

    public int calculateMinimumTotalPresses() {
        return machines.stream()
                .mapToInt(JoltageOptimizer::findMinimumPresses)
                .sum();
    }
}
