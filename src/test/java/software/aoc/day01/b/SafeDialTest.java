package software.aoc.day01.b;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class SafeDialTest {

    @Test
    public void should_calculate_correct_password_for_example_input() {
        SafeDial safeDial = new SafeDial();
        List<String> instructions = List.of(
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        );

        int password = safeDial.calculatePassword(instructions);
        assertThat(password).isEqualTo(6);
    }
}