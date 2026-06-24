package software.aoc.day05.a;

public record IngredientRange(long start, long end) {

    public boolean contains(long id) {
        return id >= start && id <= end;
    }
}
