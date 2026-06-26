package software.aoc.day07.a;

import java.util.Set;

public class BeamPropagator {

    public PropagationStep advance(Set<Integer> currentBeams, String row) {
        return currentBeams.stream()
                .map(col -> evaluateBeam(col, row))
                .reduce(PropagationStep.empty(), PropagationStep::merge);
    }

    private PropagationStep evaluateBeam(int col, String row) {
        return isSplitter(col, row)
                ? PropagationStep.splitEvent(col)
                : PropagationStep.freeTransmission(col);
    }

    private boolean isSplitter(int col, String row) {
        return row.charAt(col) == '^';
    }
}
