package software.aoc.day05.a;

import java.util.List;

public final class InventoryParser {

    private InventoryParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static InventoryData parse(List<String> input) {
        int separatorIndex = input.indexOf("");

        List<IngredientRange> ranges = input.stream()
                .limit(separatorIndex)
                .map(InventoryParser::parseRange)
                .toList();

        List<Long> availableIds = input.stream()
                .skip(separatorIndex + 1)
                .map(Long::parseLong)
                .toList();

        return new InventoryData(ranges, availableIds);
    }

    private static IngredientRange parseRange(String line) {
        String[] bounds = line.split("-");
        return new IngredientRange(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
    }
}
