package software.aoc.day05.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FreshIngredientCounterTest {

    @Test
    public void should_count_fresh_ingredients_correctly() {
        List<IngredientRange> ranges = List.of(
                new IngredientRange(3, 5),
                new IngredientRange(10, 14),
                new IngredientRange(16, 20),
                new IngredientRange(12, 18)
        );
        List<Integer> availableIds = List.of(1, 5, 8, 11, 17, 32);

        assertThat(new FreshIngredientCounter(ranges).countFresh(availableIds)).isEqualTo(3);
    }
}
