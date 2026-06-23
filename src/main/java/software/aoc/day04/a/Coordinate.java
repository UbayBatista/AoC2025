package software.aoc.day04.a;

import java.util.stream.Stream;

public record Coordinate(int x, int y) {

    public Stream<Coordinate> neighbors() {
        return Stream.of(
                new Coordinate(x - 1, y - 1), new Coordinate(x, y - 1), new Coordinate(x + 1, y - 1),
                new Coordinate(x - 1, y),                                     new Coordinate(x + 1, y),
                new Coordinate(x - 1, y + 1), new Coordinate(x, y + 1), new Coordinate(x + 1, y + 1)
        );
    }
}
