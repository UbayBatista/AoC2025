package software.aoc.day05.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryParserTest {

    @Test
    public void should_parse_input_into_inventory_data() {
        List<String> input = List.of(
                "3-5",
                "10-14",
                "",
                "1",
                "5"
        );
        InventoryData data = InventoryParser.parse(input);

        assertThat(data.ranges()).containsExactly(
                new IngredientRange(3L, 5L),
                new IngredientRange(10L, 14L)
        );
        assertThat(data.availableIds()).containsExactly(1L, 5L);
    }
}
