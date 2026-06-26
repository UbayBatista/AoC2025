package software.aoc.day06.a;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WorksheetEvaluatorTest {

    @Test
    public void should_calculate_grand_total_for_example_input() {
        List<String> rawInput = List.of(
                "123 328  51 64 ",
                " 45 64  387 23 ",
                "  6 98  215 314",
                "*   +   *   +  "
        );

        assertThat(WorksheetEvaluator.calculateGrandTotal(rawInput)).isEqualTo(4277556L);
    }
}
