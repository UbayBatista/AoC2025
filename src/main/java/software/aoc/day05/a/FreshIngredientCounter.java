package software.aoc.day05.a;

import java.util.List;

public class FreshIngredientCounter {

    private final List<IngredientRange> ranges;

    public FreshIngredientCounter(List<IngredientRange> ranges) {
        this.ranges = List.copyOf(ranges);
    }

    public long countFresh(List<Long> availableIds) {
        return availableIds.stream()
                .filter(this::isFresh)
                .count();
    }

    private boolean isFresh(long id) {
        return ranges.stream()
                .anyMatch(range -> range.contains(id));
    }
}
