package software.aoc.day12.a;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TreeRegionPacker {

    private TreeRegionPacker() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final class WorkspaceGrid {
        private final boolean[][] grid;
        private final int width;
        private final int height;

        public WorkspaceGrid(int width, int height) {
            this.width = width;
            this.height = height;
            this.grid = new boolean[height][width];
        }

        public boolean canPlace(Set<Point> shapeBlocks, int startX, int startY) {
            return shapeBlocks.stream().noneMatch(p ->
                    isOutOfBounds(startX + p.x(), startY + p.y()) || grid[startY + p.y()][startX + p.x()]
            );
        }

        private boolean isOutOfBounds(int x, int y) {
            return x < 0 || x >= width || y < 0 || y >= height;
        }

        public boolean place(Set<Point> shapeBlocks, int startX, int startY) {
            shapeBlocks.forEach(p -> grid[startY + p.y()][startX + p.x()] = true);
            return true;
        }

        public boolean remove(Set<Point> shapeBlocks, int startX, int startY) {
            shapeBlocks.forEach(p -> grid[startY + p.y()][startX + p.x()] = false);
            return false;
        }
    }

    public static boolean canPack(TreeRegion region, List<PresentShape> availableShapes) {
        List<Integer> pieces = region.flattenRequirements();

        return minPresentArea(pieces, availableShapes) <= (region.width() * region.height()) &&
                backtrack(
                        sortPiecesByAscendingSize(pieces, availableShapes),
                        new WorkspaceGrid(region.width(), region.height()),
                        buildPlacementCache(availableShapes),
                        region
                );
    }

    private static boolean backtrack(List<Integer> pending, WorkspaceGrid grid,
                                     Map<Integer, Set<PresentShape>> cache, TreeRegion region) {
        return pending.isEmpty() || processNextPiece(pending, grid, cache, region);
    }

    private static boolean processNextPiece(List<Integer> pending, WorkspaceGrid grid,
                                            Map<Integer, Set<PresentShape>> cache, TreeRegion region) {

        int currentPieceId = pending.getLast();
        List<Integer> remaining = pending.subList(0, pending.size() - 1);

        return cache.get(currentPieceId).stream().anyMatch(variant ->
                IntStream.range(0, region.height()).anyMatch(y ->
                        IntStream.range(0, region.width()).anyMatch(x ->
                                grid.canPlace(variant.blocks(), x, y) &&
                                        evaluatePlacement(variant.blocks(), x, y, remaining, grid, cache, region)
                        )
                )
        );
    }

    private static boolean evaluatePlacement(Set<Point> blocks, int x, int y, List<Integer> remaining,
                                             WorkspaceGrid grid, Map<Integer, Set<PresentShape>> cache, TreeRegion region) {
        return (grid.place(blocks, x, y) && backtrack(remaining, grid, cache, region)) || grid.remove(blocks, x, y);
    }

    private static Map<Integer, Set<PresentShape>> buildPlacementCache(List<PresentShape> shapes) {
        return shapes.stream().collect(Collectors.toUnmodifiableMap(
                PresentShape::id,
                PresentShapePermutator::generatePermutations
        ));
    }

    private static List<Integer> sortPiecesByAscendingSize(List<Integer> pieces, List<PresentShape> shapes) {
        Map<Integer, Integer> sizeCache = shapes.stream()
                .collect(Collectors.toUnmodifiableMap(PresentShape::id, s -> s.blocks().size()));

        return pieces.stream()
                .sorted(Comparator.comparingInt(sizeCache::get))
                .toList();
    }

    private static int minPresentArea(List<Integer> pieces, List<PresentShape> shapes) {
        return pieces.stream().mapToInt(id -> getShapeById(id, shapes).blocks().size()).sum();
    }

    private static PresentShape getShapeById(int id, List<PresentShape> shapes) {
        return shapes.stream().filter(s -> s.id() == id).findFirst().orElseThrow();
    }
}
