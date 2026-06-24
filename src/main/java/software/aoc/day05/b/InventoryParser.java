package software.aoc.day05.b;

import java.util.List;

public final class InventoryParser {

    private InventoryParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static List<IngredientRange> parseRanges(List<String> input) {
        return input.stream()
                .takeWhile(line -> !line.trim().isEmpty())
                .map(InventoryParser::parseRange)
                .toList();
    }

    private static IngredientRange parseRange(String line) {
        String[] bounds = line.split("-");
        return new IngredientRange(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
    }
}
