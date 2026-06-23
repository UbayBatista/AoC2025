package software.aoc.day04.a;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaperGrid {

    private static final char PAPER_ROLL = '@';
    private final Set<Coordinate> rolls;

    private PaperGrid(Set<Coordinate> rolls) {
        this.rolls = Set.copyOf(rolls);
    }

    public static PaperGrid from(List<String> input) {
        return new PaperGrid(
                IntStream.range(0, input.size())
                        .boxed()
                        .flatMap(y -> IntStream.range(0, input.get(y).length())
                                .filter(x -> input.get(y).charAt(x) == PAPER_ROLL)
                                .mapToObj(x -> new Coordinate(x, y)))
                        .collect(Collectors.toSet())
        );
    }

    public boolean isAccessible(Coordinate target) {
        return target.neighbors()
                .filter(rolls::contains)
                .count() < 4;
    }

    public int countAccessibleRolls() {
        return Math.toIntExact(
                rolls.stream()
                        .filter(this::isAccessible)
                        .count()
        );
    }
}
