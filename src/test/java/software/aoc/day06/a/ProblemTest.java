package software.aoc.day06.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ProblemTest {

    @Test
    public void should_evaluate_single_addition_problem() {
        assertThat(new Problem(List.of(328L, 64L, 98L), Operator.ADD).evaluate()).isEqualTo(490L);
    }

    @Test
    public void should_evaluate_single_multiplication_problem() {
        assertThat(new Problem(List.of(123L, 45L, 6L), Operator.MULTIPLY).evaluate()).isEqualTo(33210L);
    }
}
