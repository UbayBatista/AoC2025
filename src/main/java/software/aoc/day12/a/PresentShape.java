package software.aoc.day12.a;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record PresentShape(int id, Set<Point> blocks) {

    public static PresentShape parse(String block) {
        List<String> lines = Arrays.asList(block.split("\n"));
        return new PresentShape(
                Integer.parseInt(lines.get(0).replace(":", "")),
                extractBlocks(lines)
        );
    }

    private static Set<Point> extractBlocks(List<String> lines) {
        return IntStream.range(1, lines.size()).boxed()
                .flatMap(y -> IntStream.range(0, lines.get(y).length())
                        .filter(x -> lines.get(y).charAt(x) == '#')
                        .mapToObj(x -> new Point(x, y - 1)))
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean isNormalized() {
        return blocks.stream().anyMatch(p -> p.x() == 0) &&
                blocks.stream().anyMatch(p -> p.y() == 0);
    }

    public PresentShape normalize() {
        return new PresentShape(id, shiftBlocks(normalizationOffset(blocks)));
    }

    private static Point normalizationOffset(Set<Point> blocks) {
        Point min = minPoint(blocks);
        return new Point(-min.x(), -min.y());
    }

    private static Point minPoint(Set<Point> blocks) {
        return blocks.stream()
                .reduce((currentMin, p) -> new Point(
                        Math.min(currentMin.x(), p.x()),
                        Math.min(currentMin.y(), p.y())
                ))
                .orElseThrow();
    }

    private Set<Point> shiftBlocks(Point offset) {
        return blocks.stream()
                .map(p -> p.add(offset))
                .collect(Collectors.toUnmodifiableSet());
    }
}