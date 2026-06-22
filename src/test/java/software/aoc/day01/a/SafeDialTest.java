import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SafeDialTest {

    private SafeDial safeDial;

    @BeforeEach
    public void setUp() {
        this.safeDial = new SafeDial();
    }

    @Test
    public void should_calculate_correct_password_for_example_input() {
        List<String> instructions = List.of(
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        );

        int password = safeDial.calculatePassword(instructions);
        assertThat(password).isEqualTo(3);
    }

    @Test
    public void should_rotate_right_without_wrapping() {
        int nextPosition = safeDial.applyRotation(50, "R10");
        assertThat(nextPosition).isEqualTo(60);
    }

    @Test
    public void should_rotate_left_without_wrapping() {
        int nextPosition = safeDial.applyRotation(50, "L10");
        assertThat(nextPosition).isEqualTo(40);
    }

    @Test
    public void should_wrap_around_when_rotating_right_past_maximum() {
        int nextPosition = safeDial.applyRotation(95, "R10");
        assertThat(nextPosition).isEqualTo(5);
    }

    @Test
    public void should_wrap_around_when_rotating_left_past_zero() {
        int nextPosition = safeDial.applyRotation(5, "L10");
        assertThat(nextPosition).isEqualTo(95);
    }
}