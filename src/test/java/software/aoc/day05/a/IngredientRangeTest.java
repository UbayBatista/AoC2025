package software.aoc.day05.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientRangeTest {

    @Test
    public void should_return_true_when_id_is_within_inclusive_bounds() {
        IngredientRange range = new IngredientRange(3, 5);

        assertThat(range.contains(3)).isTrue();
        assertThat(range.contains(4)).isTrue();
        assertThat(range.contains(5)).isTrue();
    }

    @Test
    public void should_return_false_when_id_is_outside_bounds() {
        IngredientRange range = new IngredientRange(3, 5);

        assertThat(range.contains(2)).isFalse();
        assertThat(range.contains(6)).isFalse();
    }
}
