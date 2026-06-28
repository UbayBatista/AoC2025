package software.aoc.day09.a;

import static java.lang.Long.parseLong;

public record Tile(long x, long y) {

    public static Tile fromString(String tileCoordinates) {
        return parse(tileCoordinates.split(","));
    }

    private static Tile parse(String[] tileCoordinates) {
        return new Tile(
                parseLong(tileCoordinates[0].trim()),
                parseLong(tileCoordinates[1].trim())
        );
    }

    public long calculateAreaWith(Tile other) {
        return (Math.abs(this.x - other.x) + 1) * (Math.abs(this.y - other.y) + 1);
    }
}
