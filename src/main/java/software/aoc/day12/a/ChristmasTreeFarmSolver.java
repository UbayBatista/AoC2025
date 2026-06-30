package software.aoc.day12.a;

import java.util.List;

public class ChristmasTreeFarmSolver {

    public long solve(List<String> input) {
        ChristmasTreeFarm farm = ChristmasTreeFarm.from(input);
        return farm.regions().stream()
                .filter(region -> TreeRegionPacker.canPack(region, farm.shapes()))
                .count();
    }
}
