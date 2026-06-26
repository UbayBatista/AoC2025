package software.aoc.day08.a;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class CircuitTracker {

    private final int[] parent;
    private final int[] size;

    public CircuitTracker(int elements) {
        this.parent = IntStream.range(0, elements).toArray();
        this.size = IntStream.generate(() -> 1).limit(elements).toArray();
    }

    private int find(int i) {
        return parent[i] == i ? i : (parent[i] = find(parent[i]));
    }

    public void union(int i, int j) {
        Optional.of(new int[]{find(i), find(j)})
                .filter(roots -> roots[0] != roots[1])
                .ifPresent(this::linkRoots);
    }

    private void linkRoots(int[] roots) {
        int rootI = roots[0];
        int rootJ = roots[1];

        int smaller = size[rootI] < size[rootJ] ? rootI : rootJ;
        int larger = size[rootI] < size[rootJ] ? rootJ : rootI;

        parent[smaller] = larger;
        size[larger] += size[smaller];
    }

    public List<Integer> getLargestCircuitSizes(int limit) {
        return IntStream.range(0, parent.length)
                .filter(i -> parent[i] == i)
                .map(i -> size[i])
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(limit)
                .toList();
    }
}
