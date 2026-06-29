package software.aoc.day12.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChristmasTreeFarmSolverTest {

    @Test
    public void should_count_valid_regions_from_tree_farm_input() {
        assertThat(new ChristmasTreeFarmSolver().solve(TreeFarmTestData.EXAMPLE_INPUT)).isEqualTo(2L);
    }
}
