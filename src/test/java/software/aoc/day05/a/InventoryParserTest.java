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
                new IngredientRange(3, 5),
                new IngredientRange(10, 14)
        );
        assertThat(data.availableIds()).containsExactly(1, 5);
    }
}
