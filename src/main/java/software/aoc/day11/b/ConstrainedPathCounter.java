package software.aoc.day11.b;

import java.util.HashMap;
import java.util.Map;

public final class ConstrainedPathCounter {

    private ConstrainedPathCounter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long countPaths(DeviceGraph graph, String start, String target, String req1, String req2) {
        PathContext initialContext = new PathContext(start, start.equals(req1), start.equals(req2));
        RecursivePathFinder pathFinder = new RecursivePathFinder(graph, target, req1, req2);

        return pathFinder.findFrom(initialContext);
    }

    private static final class RecursivePathFinder {
        private final DeviceGraph graph;
        private final String target;
        private final String req1;
        private final String req2;
        private final Map<PathContext, Long> cache;

        private RecursivePathFinder(DeviceGraph graph, String target, String req1, String req2) {
            this.graph = graph;
            this.target = target;
            this.req1 = req1;
            this.req2 = req2;
            this.cache = new HashMap<>();
        }

        public long findFrom(PathContext context) {
            if (isTargetReached(context)) {
                return context.hasVisitedAllRequirements() ? 1L : 0L;
            }
            return getCachedOrCompute(context);
        }

        private boolean isTargetReached(PathContext context) {
            return context.current().equals(target);
        }

        private long getCachedOrCompute(PathContext context) {
            if (cache.containsKey(context)) {
                return cache.get(context);
            }

            long computedPaths = computePathsForNeighbors(context);
            cache.put(context, computedPaths);

            return computedPaths;
        }

        private long computePathsForNeighbors(PathContext context) {
            return graph.getOutputs(context.current())
                    .stream()
                    .mapToLong(neighbor -> findFrom(context.traverseTo(neighbor, req1, req2)))
                    .sum();
        }
    }

    private record PathContext(String current, boolean visitedReq1, boolean visitedReq2) {
        public PathContext traverseTo(String nextNode, String req1, String req2) {
            return new PathContext(
                    nextNode,
                    this.visitedReq1 || nextNode.equals(req1),
                    this.visitedReq2 || nextNode.equals(req2)
            );
        }

        public boolean hasVisitedAllRequirements() {
            return visitedReq1 && visitedReq2;
        }
    }
}
