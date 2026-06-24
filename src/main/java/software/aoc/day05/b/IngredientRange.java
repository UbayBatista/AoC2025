package software.aoc.day05.b;

public record IngredientRange(long start, long end) implements Comparable<IngredientRange>{

    public boolean contains(long id) {
        return id >= start && id <= end;
    }

    public long capacity() {
        return end - start + 1;
    }

    @Override
    public int compareTo(IngredientRange other) {
        return Long.compare(this.start, other.start);
    }
}
