package software.aoc.day05.a;

import java.util.List;

public record InventoryData(List<IngredientRange> ranges, List<Long> availableIds) {

    public InventoryData {
        ranges = List.copyOf(ranges);
        availableIds = List.copyOf(availableIds);
    }
}
