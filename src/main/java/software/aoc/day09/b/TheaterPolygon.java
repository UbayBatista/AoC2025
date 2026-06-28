package software.aoc.day09.b;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class TheaterPolygon {

    private final List<Tile> vertices;

    public TheaterPolygon(List<Tile> vertices) {
        this.vertices = List.copyOf(vertices);
    }

    public boolean containsRectangle(Tile t1, Tile t2) {
        return generateRectangleTiles(t1, t2).allMatch(this::containsTile);
    }

    private Stream<Tile> generateRectangleTiles(Tile t1, Tile t2) {
        return LongStream.rangeClosed(Math.min(t1.x(), t2.x()), Math.max(t1.x(), t2.x()))
                .boxed()
                .flatMap(x -> LongStream.rangeClosed(Math.min(t1.y(), t2.y()), Math.max(t1.y(), t2.y()))
                        .mapToObj(y -> new Tile(x, y)));
    }

    private boolean containsTile(Tile tile) {
        return isOnBoundary(tile) || isInsidePolygon(tile);
    }

    private boolean isOnBoundary(Tile tile) {
        return IntStream.range(0, vertices.size())
                .anyMatch(i -> isPointOnOrthogonalSegment(
                        tile,
                        vertices.get(i),
                        vertices.get((i + 1) % vertices.size())
                ));
    }

    private boolean isPointOnOrthogonalSegment(Tile p, Tile v1, Tile v2) {
        return isPointOnVerticalSegment(p, v1, v2) || isPointOnHorizontalSegment(p, v1, v2);
    }

    private boolean isPointOnVerticalSegment(Tile p, Tile v1, Tile v2) {
        return p.x() == v1.x() && p.x() == v2.x() && isBetween(p.y(), v1.y(), v2.y());
    }

    private boolean isPointOnHorizontalSegment(Tile p, Tile v1, Tile v2) {
        return p.y() == v1.y() && p.y() == v2.y() && isBetween(p.x(), v1.x(), v2.x());
    }

    private boolean isBetween(long value, long bound1, long bound2) {
        return value >= Math.min(bound1, bound2) && value <= Math.max(bound1, bound2);
    }

    private boolean isInsidePolygon(Tile tile) {
        return countRayIntersections(tile) % 2 != 0;
    }

    private long countRayIntersections(Tile tile) {
        return IntStream.range(0, vertices.size())
                .filter(i -> intersectsRay(tile, vertices.get(i), vertices.get((i + 1) % vertices.size())))
                .count();
    }

    private boolean intersectsRay(Tile tile, Tile v1, Tile v2) {
        return isVerticalSegment(v1, v2) &&
                isRayIntersectingVerticalBounds(tile, v1, v2) &&
                isRayStartingBeforeSegment(tile, v1);
    }

    private boolean isVerticalSegment(Tile v1, Tile v2) {
        return v1.x() == v2.x();
    }

    private boolean isRayIntersectingVerticalBounds(Tile tile, Tile v1, Tile v2) {
        return tile.y() > Math.min(v1.y(), v2.y()) && tile.y() <= Math.max(v1.y(), v2.y());
    }

    private boolean isRayStartingBeforeSegment(Tile tile, Tile v1) {
        return tile.x() < v1.x();
    }
}
