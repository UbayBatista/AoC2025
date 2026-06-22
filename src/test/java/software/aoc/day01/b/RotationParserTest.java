package software.aoc.day01.b;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RotationParserTest {

    @Test
    public void should_parse_left_rotation_as_negative_step() {
        assertThat(RotationParser.parse("L50")).isEqualTo(-50);
    }

    @Test
    public void should_parse_right_rotation_as_positive_step() {
        assertThat(RotationParser.parse("R25")).isEqualTo(25);
    }
}
