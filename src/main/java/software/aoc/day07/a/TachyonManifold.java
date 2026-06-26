package software.aoc.day07.a;

import java.util.List;
import java.util.Set;

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
                        new PropagationStep(Set.of(getStartColumn()), 0L),
                        this::accumulate,
                        (s1, _) -> s1
                )
                .splitCount();
    }

    private PropagationStep accumulate(PropagationStep currentStep, String row) {
        PropagationStep delta = propagator.advance(currentStep.activeBeams(), row);
        return new PropagationStep(delta.activeBeams(), currentStep.splitCount() + delta.splitCount());
    }
}
