package software.aoc.day01.b;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DialStateTest {

    @Test
    public void should_rotate_right_without_wrapping() {
        DialState state = new DialState(50, 0);
        assertThat(state.next(10).position()).isEqualTo(60);
    }

    @Test
    public void should_rotate_left_without_wrapping() {
        DialState state = new DialState(50, 0);
        assertThat(state.next(-10).position()).isEqualTo(40);
    }

    @Test
    public void should_wrap_around_when_rotating_right_past_maximum() {
        DialState state = new DialState(95, 0);
        assertThat(state.next(10).position()).isEqualTo(5);
    }

    @Test
    public void should_wrap_around_when_rotating_left_past_zero() {
        DialState state = new DialState(5, 0);
        assertThat(state.next(-10).position()).isEqualTo(95);
    }

    @Test
    public void should_not_count_zero_passes_if_dial_does_not_cross_or_reach_zero() {
        DialState state = new DialState(50, 0);
        assertThat(state.next(10).zeroCount()).isEqualTo(0);
    }

    @Test
    public void should_count_one_zero_pass_if_dial_reaches_zero_exactly() {
        DialState state = new DialState(95, 0);
        assertThat(state.next(5).zeroCount()).isEqualTo(1);
    }

    @Test
    public void should_count_multiple_zero_passes_for_large_rotations() {
        DialState state = new DialState(50, 0);
        assertThat(state.next(10000).zeroCount()).isEqualTo(100);
    }
}
