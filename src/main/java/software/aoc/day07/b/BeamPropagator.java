package software.aoc.day07.b;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeamPropagator {

    public PropagationStep advance(Map<Integer, Long> currentTimelines, String row) {
        return new PropagationStep(
                currentTimelines.entrySet().stream()
                        .flatMap(entry -> evaluateBeam(entry.getKey(), entry.getValue(), row))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                Long::sum
                        )));
    }

    private Stream<Map.Entry<Integer, Long>> evaluateBeam(int col, long timelines, String row) {
        return isSplitter(col, row)
                ? splitQuantumTimelines(col, timelines)
                : preserveQuantumTimeline(col, timelines);
    }

    private Stream<Map.Entry<Integer, Long>> splitQuantumTimelines(int col, long timelines) {
        return Stream.of(Map.entry(col - 1, timelines), Map.entry(col + 1, timelines));
    }

    private Stream<Map.Entry<Integer, Long>> preserveQuantumTimeline(int col, long timelines) {
        return Stream.of(Map.entry(col, timelines));
    }

    private boolean isSplitter(int col, String row) {
        return row.charAt(col) == '^';
    }
}
