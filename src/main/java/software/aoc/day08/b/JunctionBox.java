package software.aoc.day08.b;

import java.util.Optional;

public record JunctionBox(long x, long y, long z) {

    public static JunctionBox parse(String line) {
        return Optional.of(line.split(","))
                .filter(parts -> parts.length == 3)
                .map(JunctionBox::createFromParts)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coordinate format: " + line));
    }

    private static JunctionBox createFromParts(String[] parts) {
        return new JunctionBox(
                Long.parseLong(parts[0].trim()),
                Long.parseLong(parts[1].trim()),
                Long.parseLong(parts[2].trim())
        );
    }

    public long distanceSquaredTo(JunctionBox other) {
        long dx = this.x - other.x;
        long dy = this.y - other.y;
        long dz = this.z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
