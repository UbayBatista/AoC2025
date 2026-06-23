package software.aoc.day04.a;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class PaperGridTest {

    @Test
    public void should_count_accessible_rolls_from_example() {
        List<String> input = List.of(
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@."
        );
        Assertions.assertThat(PaperGrid.from(input).countAccessibleRolls()).isEqualTo(13);
    }

    @Test
    public void should_identify_isolated_roll_as_accessible() {
        List<String> input = List.of(
                "...",
                ".@.",
                "..."
        );
        assertThat(PaperGrid.from(input).countAccessibleRolls()).isEqualTo(1);
    }

    @Test
    public void should_identify_surrounded_roll_as_inaccessible() {
        List<String> input = List.of(
                "@@@",
                "@@@",
                "@@@"
        );
        assertThat(PaperGrid.from(input).isAccessible(new Coordinate(1, 1))).isFalse();
    }
}
