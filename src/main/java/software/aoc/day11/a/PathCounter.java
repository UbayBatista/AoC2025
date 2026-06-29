package software.aoc.day11.a;

public final class PathCounter {

    private PathCounter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long countPaths(DeviceGraph graph, String current, String target) {
        if (current.equals(target)) {
            return 1L;
        }

        return graph.getOutputs(current)
                .stream()
                .mapToLong(neighbor -> countPaths(graph, neighbor, target))
                .sum();
    }
}
