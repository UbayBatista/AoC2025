package software.aoc.day12.a;

public record Point(int x, int y) {

    public Point rotate90() {
        return new Point(-y, x);
    }

    public Point flipHorizontal() {
        return new Point(-x, y);
    }

    public Point add(Point other) {
        return new Point(x + other.x(), y + other.y());
    }

    public Point subtract(Point other) {
        return new Point(x - other.x(), y - other.y());
    }
}