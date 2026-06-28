package software.aoc.day10.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GaussianSolverTest {

    @Test
    public void should_calculate_minimum_presses_for_first_machine() {
        String line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";

        assertThat(GaussianSolver.solve(EquationSystem.from(MachineParser.parse(line)))).isEqualTo(2);
    }

    @Test
    public void should_calculate_minimum_presses_for_second_machine() {
        String line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";

        assertThat(GaussianSolver.solve(EquationSystem.from(MachineParser.parse(line)))).isEqualTo(3);
    }

    @Test
    public void should_calculate_minimum_presses_for_third_machine() {
        String line = "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}";

        assertThat(GaussianSolver.solve(EquationSystem.from(MachineParser.parse(line)))).isEqualTo(2);
    }
}
