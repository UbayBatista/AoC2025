package software.aoc.day06.a;

import java.util.List;

public final class WorksheetEvaluator {

    private WorksheetEvaluator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long calculateGrandTotal(List<String> rawInput) {
        return WorksheetParser.parse(rawInput).stream()
                .mapToLong(Problem::evaluate)
                .sum();
    }
}
