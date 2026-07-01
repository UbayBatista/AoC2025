package software.aoc.day01.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DialStateTest {

    @Test
    public void should_rotate_right_without_wrapping() {
        assertThat(new DialState(50, 0).next(10).position()).isEqualTo(60);
    }

    @Test
    public void should_rotate_left_without_wrapping() {
        assertThat(new DialState(50, 0).next(-10).position()).isEqualTo(40);
    }

    @Test
    public void should_wrap_around_when_rotating_right_past_maximum() {
        assertThat(new DialState(95, 0).next(10).position()).isEqualTo(5);
    }

    @Test
    public void should_wrap_around_when_rotating_left_past_zero() {
        assertThat(new DialState(5, 0).next(-10).position()).isEqualTo(95);
    }
}
