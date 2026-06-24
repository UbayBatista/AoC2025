package software.aoc.day05.b;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalFreshIngredientCounterTest {

    @Test
    public void should_calculate_total_fresh_ingredients_with_overlapping_ranges() {
        List<IngredientRange> ranges = List.of(
                new IngredientRange(3L, 5L),
                new IngredientRange(10L, 14L),
                new IngredientRange(16L, 20L),
                new IngredientRange(12L, 18L)
        );

        assertThat(TotalFreshIngredientCounter.countTotal(ranges)).isEqualTo(14L);
    }
}
