package software.aoc.day12.a;

import java.util.Arrays;
import java.util.List;

public record ChristmasTreeFarm(List<PresentShape> shapes, List<TreeRegion> regions) {

    public static ChristmasTreeFarm from(List<String> inputLines) {
        List<String> blocks = Arrays.asList(String.join("\n", inputLines).split("\n\n"));
        return new ChristmasTreeFarm(parseShapes(blocks), parseRegions(blocks));
    }

    private static List<PresentShape> parseShapes(List<String> blocks) {
        return blocks.stream()
                .filter(b -> b.matches("(?s)\\d+:.*"))
                .map(PresentShape::parse)
                .toList();
    }

    private static List<TreeRegion> parseRegions(List<String> blocks) {
        return blocks.stream()
                .filter(b -> b.contains("x"))
                .flatMap(b -> Arrays.stream(b.split("\n")))
                .map(TreeRegion::parse)
                .toList();
    }
}
