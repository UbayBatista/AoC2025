package software.aoc.day09.b;

import java.util.List;
import java.util.stream.LongStream;

public class MovieTheater {

    private final List<Tile> tiles;
    private final TheaterPolygon polygon;

    public MovieTheater(List<Tile> tiles) {
        this.tiles = List.copyOf(tiles);
        this.polygon = new TheaterPolygon(this.tiles);
    }

    public long calculateLargestRectangleArea() {
        return tiles.stream()
                .flatMapToLong(this::calculateAreasWithAllTiles)
                .max()
                .orElse(0L);
    }

    public long calculateLargestValidRectangleArea() {
        return tiles.stream()
                .flatMapToLong(this::calculateValidAreasWithAllTiles)
                .max()
                .orElse(0L);
    }

    private LongStream calculateAreasWithAllTiles(Tile referenceTile) {
        return tiles.stream()
                .mapToLong(referenceTile::calculateAreaWith);
    }

    private LongStream calculateValidAreasWithAllTiles(Tile referenceTile) {
        return tiles.stream()
                .filter(other -> polygon.containsRectangle(referenceTile, other))
                .mapToLong(referenceTile::calculateAreaWith);
    }
}
