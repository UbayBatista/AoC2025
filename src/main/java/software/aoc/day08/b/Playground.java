package software.aoc.day08.b;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Playground {

    private final List<JunctionBox> boxes;

    public Playground(List<String> inputLines) {
        this.boxes = inputLines.stream()
                .map(JunctionBox::parse)
                .toList();
    }

    public long calculateLargestCircuitsProduct(int maxConnections, int limitCircuits) {
        CircuitTracker tracker = new CircuitTracker(boxes.size());
        applyShortestConnections(tracker, maxConnections);
        return computeProductOfLargestCircuits(tracker, limitCircuits);
    }

    public long calculateLastConnectionXProduct() {
        CircuitTracker tracker = new CircuitTracker(boxes.size());
        int requiredConnections = boxes.size() - 1;

        return generateAllConnections()
                .sorted()
                .filter(connection -> isEffectiveConnection(tracker, connection))
                .skip(requiredConnections - 1)
                .findFirst()
                .map(this::computeXProduct)
                .orElseThrow(() -> new IllegalStateException("Graph cannot be fully connected"));
    }

    private boolean isEffectiveConnection(CircuitTracker tracker, Connection connection) {
        return tracker.union(connection.indexA(), connection.indexB());
    }

    private long computeXProduct(Connection connection) {
        return boxes.get(connection.indexA()).x() * boxes.get(connection.indexB()).x();
    }

    private void applyShortestConnections(CircuitTracker tracker, int maxConnections) {
        generateAllConnections()
                .sorted()
                .limit(maxConnections)
                .forEach(connection -> tracker.union(connection.indexA(), connection.indexB()));
    }

    private long computeProductOfLargestCircuits(CircuitTracker tracker, int limitCircuits) {
        return tracker.getLargestCircuitSizes(limitCircuits).stream()
                .mapToLong(Integer::longValue)
                .reduce(1L, (a, b) -> a * b);
    }

    private Stream<Connection> generateAllConnections() {
        return IntStream.range(0, boxes.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, boxes.size())
                        .mapToObj(j -> new Connection(i, j, boxes.get(i).distanceSquaredTo(boxes.get(j))))
                );
    }
}
