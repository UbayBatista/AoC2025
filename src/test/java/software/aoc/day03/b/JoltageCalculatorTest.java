package software.aoc.day03.b;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JoltageCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "987654321111111, 987654321111",
            "811111111111119, 811111111119",
            "234234234234278, 434234234278",
            "818181911112111, 888911112111"
    })
    void should_calculate_max_12_digit_joltage_for_single_bank(String bankData, long expectedMaxJoltage) {
        assertThat(JoltageCalculator.calculateMaxJoltage(bankData)).isEqualTo(expectedMaxJoltage);
    }

    @Test
    void should_calculate_total_joltage_for_multiple_banks() {
        List<String> banks = List.of(
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"
        );
        assertThat(JoltageCalculator.calculateTotalJoltage(banks)).isEqualTo(3121910778619L);
    }

    @Test
    void should_throw_exception_when_bank_length_is_less_than_twelve() {
        assertThatThrownBy(() -> JoltageCalculator.calculateMaxJoltage("12345678901"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The battery bank must contain at least twelve elements");
    }
}
