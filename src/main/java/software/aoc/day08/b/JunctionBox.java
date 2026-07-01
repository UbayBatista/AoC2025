package software.aoc.day08.b;

import java.util.Optional;

public record JunctionBox(long x, long y, long z) {

    public static JunctionBox parse(String line) {
        return Optional.of(line.split(","))
                .filter(coords -> coords.length == 3)
                .map(JunctionBox::createFromCoords)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coordinate format: " + line));
    }

    private static JunctionBox createFromCoords(String[] coords) {
        return new JunctionBox(
                Long.parseLong(coords[0].trim()),
                Long.parseLong(coords[1].trim()),
                Long.parseLong(coords[2].trim())
        );
    }

    public long distanceSquaredTo(JunctionBox other) {
        long dx = this.x - other.x;
        long dy = this.y - other.y;
        long dz = this.z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
