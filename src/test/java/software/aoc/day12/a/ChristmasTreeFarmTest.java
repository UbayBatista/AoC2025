package software.aoc.day12.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChristmasTreeFarmTest {

    @Test
    public void should_parse_and_construct_domain_model_from_input() {
        ChristmasTreeFarm farm = ChristmasTreeFarm.from(TreeFarmTestData.EXAMPLE_INPUT);
        assertThat(farm.shapes()).hasSize(6);
        assertThat(farm.regions()).hasSize(3);

        PresentShape shape4 = farm.shapes().get(4);
        assertThat(shape4.blocks()).containsExactlyInAnyOrder(
                new Point(0, 0), new Point(1, 0), new Point(2, 0),
                new Point(0, 1),
                new Point(0, 2), new Point(1, 2), new Point(2, 2)
        );

        TreeRegion region = farm.regions().get(0);
        assertThat(region.width()).isEqualTo(4);
        assertThat(region.height()).isEqualTo(4);
        assertThat(region.requirements().get(4)).isEqualTo(2);
    }
}
