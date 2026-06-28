package software.aoc.day09.b;

import java.util.List;
import java.util.stream.IntStream;

public class TheaterPolygon {

    private final List<Tile> vertices;

    public TheaterPolygon(List<Tile> vertices) {
        this.vertices = List.copyOf(vertices);
    }

    public boolean containsRectangle(Tile pointA, Tile pointB) {
        return areAllCornersInside(pointA, pointB) && !doesAnyPolygonSegmentIntersectInterior(pointA, pointB);
    }

    private boolean areAllCornersInside(Tile pointA, Tile pointB) {
        return rectangleCorners(pointA, pointB).stream().allMatch(this::isInside);
    }

    private List<Tile> rectangleCorners(Tile pointA, Tile pointB) {
        return List.of(
                pointA,
                pointB,
                new Tile(pointA.x(), pointB.y()),
                new Tile(pointB.x(), pointA.y())
        );
    }

    private boolean doesAnyPolygonSegmentIntersectInterior(Tile pointA, Tile pointB) {
        long minX = Math.min(pointA.x(), pointB.x());
        long maxX = Math.max(pointA.x(), pointB.x());
        long minY = Math.min(pointA.y(), pointB.y());
        long maxY = Math.max(pointA.y(), pointB.y());

        return IntStream.range(0, vertices.size())
                .anyMatch(i -> segmentIntersectsInterior(
                        vertices.get(i),
                        vertices.get((i + 1) % vertices.size()),
                        minX, maxX, minY, maxY
                ));
    }

    private boolean segmentIntersectsInterior(Tile v1, Tile v2, long minX, long maxX, long minY, long maxY) {
        return isVerticalIntersection(v1, v2, minX, maxX, minY, maxY) ||
                isHorizontalIntersection(v1, v2, minX, maxX, minY, maxY);
    }

    private boolean isVerticalIntersection(Tile v1, Tile v2, long minX, long maxX, long minY, long maxY) {
        return isSegmentVertical(v1, v2) &&
                isStrictlyBetween(v1.x(), minX, maxX) &&
                isOverlapping(v1.y(), v2.y(), minY, maxY);
    }

    private boolean isHorizontalIntersection(Tile v1, Tile v2, long minX, long maxX, long minY, long maxY) {
        return isSegmentHorizontal(v1, v2) &&
                isStrictlyBetween(v1.y(), minY, maxY) &&
                isOverlapping(v1.x(), v2.x(), minX, maxX);
    }

    private boolean isSegmentVertical(Tile segmentStart, Tile segmentEnd) {
        return segmentStart.x() == segmentEnd.x();
    }

    private boolean isSegmentHorizontal(Tile segmentStart, Tile segmentEnd) {
        return segmentStart.y() == segmentEnd.y();
    }

    private boolean isStrictlyBetween(long value, long min, long max) {
        return value > min && value < max;
    }

    private boolean isOverlapping(long bound1, long bound2, long min, long max) {
        return Math.min(bound1, bound2) < max && Math.max(bound1, bound2) > min;
    }

    private boolean isInside(Tile point) {
        return hasOddIntersections(point) || isOnBoundary(point);
    }

    private boolean hasOddIntersections(Tile point) {
        return countIntersections(point) % 2 != 0;
    }

    private long countIntersections(Tile point) {
        return IntStream.range(0, vertices.size())
                .filter(i -> rayIntersects(point, vertices.get(i), vertices.get((i + 1) % vertices.size())))
                .count();
    }

    private boolean rayIntersects(Tile point, Tile segmentStart, Tile segmentEnd) {
        if (isSegmentHorizontal(segmentStart, segmentEnd)) return false;
        return isRayVerticallyAligned(point, segmentStart, segmentEnd)
                && isIntersectionToRightOfPoint(point, computeIntersectionX(point, segmentStart, segmentEnd));
    }

    private double computeIntersectionX(Tile point, Tile segmentStart, Tile segmentEnd) {
        double dySegment = segmentEnd.y() - segmentStart.y();
        double dxSegment = segmentEnd.x() - segmentStart.x();
        double dyPoint = point.y() - segmentStart.y();
        return segmentStart.x() + dyPoint * dxSegment / dySegment;
    }

    private boolean isRayVerticallyAligned(Tile point, Tile segmentStart, Tile segmentEnd) {
        return point.y() >= Math.min(segmentStart.y(), segmentEnd.y())
                && point.y() <  Math.max(segmentStart.y(), segmentEnd.y());
    }

    private boolean isIntersectionToRightOfPoint(Tile point, double intersectX) {
        return point.x() < intersectX;
    }

    private boolean isOnBoundary(Tile point) {
        return IntStream.range(0, vertices.size())
                .anyMatch(i -> isPointOnSegment(point, vertices.get(i), vertices.get((i + 1) % vertices.size())));
    }

    private boolean isPointOnSegment(Tile point, Tile segmentStart, Tile segmentEnd) {
        return isCollinear(point, segmentStart, segmentEnd) &&
                isWithinBounds(point, segmentStart, segmentEnd);
    }

    private boolean isCollinear(Tile point, Tile segmentStart, Tile segmentEnd) {
        return crossProduct(segmentStart, segmentEnd, point) == 0;
    }

    private long crossProduct(Tile segmentStart, Tile segmentEnd, Tile point) {
        long segmentDx = segmentEnd.x() - segmentStart.x();
        long segmentDy = segmentEnd.y() - segmentStart.y();
        long pointDx = point.x() - segmentStart.x();
        long pointDy = point.y() - segmentStart.y();
        return segmentDx * pointDy - segmentDy * pointDx;
    }

    private boolean isWithinBounds(Tile point, Tile segmentStart, Tile segmentEnd) {
        return isWithinXBounds(point, segmentStart, segmentEnd) &&
                isWithinYBounds(point, segmentStart, segmentEnd);
    }

    private boolean isWithinXBounds(Tile point, Tile segmentStart, Tile segmentEnd) {
        return point.x() >= Math.min(segmentStart.x(), segmentEnd.x()) &&
                point.x() <= Math.max(segmentStart.x(), segmentEnd.x());
    }

    private boolean isWithinYBounds(Tile point, Tile segmentStart, Tile segmentEnd) {
        return point.y() >= Math.min(segmentStart.y(), segmentEnd.y()) &&
                point.y() <= Math.max(segmentStart.y(), segmentEnd.y());
    }
}
