package software.aoc.day01.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class SafeDialTest {

    @Test
    public void should_calculate_correct_password_for_example_input() {
        List<String> instructions = List.of(
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        );

        assertThat(new SafeDial().calculatePassword(instructions)).isEqualTo(3);
    }
}