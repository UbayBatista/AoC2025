package software.aoc.day12.a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TreeRegionPackerTest {

    private ChristmasTreeFarm farm;

    @BeforeEach
    public void setUp() {
        farm = ChristmasTreeFarm.from(TreeFarmTestData.EXAMPLE_INPUT);
    }

    @Test
    public void should_pack_4x4_region_successfully() {
        assertThat(TreeRegionPacker.canPack(farm.regions().get(0), farm.shapes())).isTrue();
    }

    @Test
    public void should_pack_12x5_region_with_base_requirements_successfully() {
        assertThat(TreeRegionPacker.canPack(farm.regions().get(1), farm.shapes())).isTrue();
    }

    @Test
    public void should_fail_to_pack_overcrowded_12x5_region() {
        assertThat(TreeRegionPacker.canPack(farm.regions().get(2), farm.shapes())).isFalse();
    }
}
