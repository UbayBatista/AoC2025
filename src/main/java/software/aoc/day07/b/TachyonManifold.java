package software.aoc.day07.b;

import java.util.List;
import java.util.Map;

public class TachyonManifold {

    private final List<String> grid;
    private final BeamPropagator propagator;

    public TachyonManifold(List<String> grid) {
        this.grid = List.copyOf(grid);
        this.propagator = new BeamPropagator();
    }

    public int getStartColumn() {
        return grid.get(0).indexOf('S');
    }

    public long simulate() {
        return grid.stream()
                .skip(1)
                .reduce(
                        new PropagationStep(Map.of(getStartColumn(), 1L)),
                        this::accumulate,
                        (s1, _) -> s1
                )
                .activeTimelines()
                .values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private PropagationStep accumulate(PropagationStep currentStep, String row) {
        return propagator.advance(currentStep.activeTimelines(), row);
    }
}
