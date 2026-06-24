package software.aoc.day05.b;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class TotalFreshIngredientCounter {

    private TotalFreshIngredientCounter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long countTotal(List<IngredientRange> ranges) {
        return merge(ranges).stream()
                .mapToLong(IngredientRange::capacity)
                .sum();
    }

    private static List<IngredientRange> merge(List<IngredientRange> ranges) {
        return ranges.stream()
                .sorted()
                .collect(LinkedList::new, TotalFreshIngredientCounter::accumulateRange, LinkedList::addAll)
                .stream()
                .toList();
    }

    private static void accumulateRange(LinkedList<IngredientRange> accumulator, IngredientRange current) {
        Optional.ofNullable(accumulator.peekLast())
                .filter(last -> overlaps(last, current))
                .ifPresentOrElse(
                        last -> mergeWithLast(accumulator, last, current),
                        () -> accumulator.add(current)
                );
    }

    private static boolean overlaps(IngredientRange left, IngredientRange right) {
        return left.end() >= right.start();
    }

    private static void mergeWithLast(LinkedList<IngredientRange> accumulator, IngredientRange last, IngredientRange current) {
        accumulator.removeLast();
        accumulator.add(new IngredientRange(last.start(), Math.max(last.end(), current.end())));
    }
}