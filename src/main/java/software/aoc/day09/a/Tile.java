package software.aoc.day09.a;

public record Tile(long x, long y) {

    public static Tile fromString(String line) {
        String[] coordinates = line.split(",");
        return new Tile(
                Long.parseLong(coordinates[0].trim()),
                Long.parseLong(coordinates[1].trim())
        );
    }

    public long calculateAreaWith(Tile other) {
        return (Math.abs(this.x - other.x) + 1) * (Math.abs(this.y - other.y) + 1);
    }
}
