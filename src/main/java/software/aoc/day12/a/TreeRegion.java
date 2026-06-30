package software.aoc.day12.a;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record TreeRegion(int width, int height, Map<Integer, Integer> requirements) {

    public static TreeRegion parse(String line) {
        String[] sections  = line.split(": ");
        String[] dims = sections[0].split("x");
        String[] reqs = sections[1].split(" ");

        return new TreeRegion(
                Integer.parseInt(dims[0]),
                Integer.parseInt(dims[1]),
                extractRequirements(reqs)
        );
    }

    private static Map<Integer, Integer> extractRequirements(String[] reqs) {
        return IntStream.range(0, reqs.length).boxed()
                .filter(i -> Integer.parseInt(reqs[i]) > 0)
                .collect(Collectors.toUnmodifiableMap(i -> i, i -> Integer.parseInt(reqs[i])));
    }

    public List<Integer> flattenRequirements() {
        return requirements.entrySet().stream()
                .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())
                .toList();
    }
}
