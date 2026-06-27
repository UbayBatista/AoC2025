package software.aoc.day09.a;

import java.util.List;
import java.util.stream.LongStream;

public class MovieTheater {

    private final List<Tile> tiles;

    public MovieTheater(List<Tile> tiles) {
        this.tiles = List.copyOf(tiles);
    }

    public long calculateLargestRectangleArea() {
        return tiles.stream()
                .flatMapToLong(this::calculateAreasWithAllTiles)
                .max()
                .orElse(0L);
    }

    private LongStream calculateAreasWithAllTiles(Tile referenceTile) {
        return tiles.stream()
                .mapToLong(referenceTile::calculateAreaWith);
    }
}
