package software.aoc.day05.b;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryParserTest {

    @Test
    public void should_parse_only_ranges_and_ignore_irrelevant_section() {
        List<String> input = List.of(
                "3-5",
                "10-14",
                "",
                "1",
                "5"
        );

        assertThat(InventoryParser.parseRanges(input)).containsExactly(
                new IngredientRange(3L, 5L),
                new IngredientRange(10L, 14L)
        );
    }
}
