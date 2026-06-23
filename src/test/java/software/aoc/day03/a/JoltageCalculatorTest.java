package software.aoc.day03.a;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JoltageCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "987654321111111, 98",
            "811111111111119, 89",
            "234234234234278, 78",
            "818181911112111, 92"
    })
    public void should_calculate_max_joltage_for_single_bank(String bankData, int expectedMaxJoltage) {
        assertThat(JoltageCalculator.calculateMaxJoltage(bankData)).isEqualTo(expectedMaxJoltage);
    }

    @Test
    public void should_calculate_total_joltage_for_multiple_banks() {
        List<String> banks = List.of(
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"
        );
        assertThat(JoltageCalculator.calculateTotalJoltage(banks)).isEqualTo(357);
    }

    @Test
    public void should_throw_exception_when_bank_length_is_less_than_two() {
        assertThatThrownBy(() -> JoltageCalculator.calculateMaxJoltage("9"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The battery bank must contain at least two elements.");
    }
}
