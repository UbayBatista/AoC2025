package software.aoc.day10.b;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MachineParserTest {

    @Test
    public void should_parse_valid_machine_ignoring_light_states_and_extracting_joltages() {
        String input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
        Machine machine = MachineParser.parse(input);

        assertThat(machine.targetJoltages()).containsExactly(3, 5, 4, 7);
        assertThat(machine.buttons()).hasSize(6);
        assertThat(machine.buttons().get(1).affectedCounters()).containsExactly(1, 3);
    }
}
