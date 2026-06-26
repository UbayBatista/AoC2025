package software.aoc.day08.b;

public record Connection(int indexA, int indexB, long distanceSquared) implements Comparable<Connection> {

    @Override
    public int compareTo(Connection other) {
        return Long.compare(this.distanceSquared, other.distanceSquared);
    }
}
