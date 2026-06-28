package software.aoc.day10.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MachineParserTest {

    @Test
    public void should_parse_valid_machine_string_and_ignore_joltage() {
        String input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
        Machine machine = MachineParser.parse(input);

        assertThat(machine.targetState()).isEqualTo(".##.");
        assertThat(machine.buttons()).hasSize(6);
        assertThat(machine.buttons().get(1).toggledLightIndices()).containsExactly(1, 3);
    }
}
