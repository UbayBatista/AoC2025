package software.aoc.day01.b;

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

    @Test
    public void should_not_count_zero_passes_if_dial_does_not_cross_or_reach_zero() {
        assertThat(new DialState(50, 0).next(10).zeroCount()).isEqualTo(0);
    }

    @Test
    public void should_count_one_zero_pass_if_dial_reaches_zero_exactly() {
        assertThat(new DialState(95, 0).next(5).zeroCount()).isEqualTo(1);
    }

    @Test
    public void should_count_multiple_zero_passes_for_large_rotations() {
        assertThat(new DialState(50, 0).next(10000).zeroCount()).isEqualTo(100);
    }
}
