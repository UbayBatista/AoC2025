package software.aoc.day02.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InvalidGiftIdCalculatorTest {

    @Test
    public void should_identify_repeating_sequence_as_invalid_id() {
        assertThat(InvalidGiftIdCalculator.isInvalidId(55L)).isTrue();
        assertThat(InvalidGiftIdCalculator.isInvalidId(6464L)).isTrue();
        assertThat(InvalidGiftIdCalculator.isInvalidId(123123L)).isTrue();
    }

    @Test
    public void should_identify_non_repeating_sequence_as_valid_id() {
        assertThat(InvalidGiftIdCalculator.isInvalidId(101L)).isFalse();
        assertThat(InvalidGiftIdCalculator.isInvalidId(123456L)).isFalse();
    }

    @Test
    public void should_calculate_sum_for_single_range() {
        String range = "11-22";
        long sum = InvalidGiftIdCalculator.calculateSumOfInvalidIds(range);
        assertThat(sum).isEqualTo(33L);
    }

    @Test
    public void should_calculate_sum_for_example_input() {
        String input = """
                       11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                       1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                       824824821-824824827,2121212118-2121212124
                       """;
        long sum = InvalidGiftIdCalculator.calculateSumOfInvalidIds(input);
        assertThat(sum).isEqualTo(1227775554L);
    }
}
