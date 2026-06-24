package software.aoc.day05.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FreshIngredientCounterTest {

    @Test
    public void should_count_fresh_ingredients_correctly() {
        List<IngredientRange> ranges = List.of(
                new IngredientRange(3L, 5L),
                new IngredientRange(10L, 14L),
                new IngredientRange(16L, 20L),
                new IngredientRange(12L, 18L)
        );
        List<Long> availableIds = List.of(1L, 5L, 8L, 11L, 17L, 32L);

        assertThat(new FreshIngredientCounter(ranges).countFresh(availableIds)).isEqualTo(3L);
    }
}
