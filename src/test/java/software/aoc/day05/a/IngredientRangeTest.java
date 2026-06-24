package software.aoc.day05.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientRangeTest {

    @Test
    public void should_return_true_when_id_is_within_inclusive_bounds() {
        IngredientRange range = new IngredientRange(3L, 5L);

        assertThat(range.contains(3L)).isTrue();
        assertThat(range.contains(4L)).isTrue();
        assertThat(range.contains(5L)).isTrue();
    }

    @Test
    public void should_return_false_when_id_is_outside_bounds() {
        IngredientRange range = new IngredientRange(3L, 5L);

        assertThat(range.contains(2L)).isFalse();
        assertThat(range.contains(6L)).isFalse();
    }
}
