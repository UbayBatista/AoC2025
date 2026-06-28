package software.aoc.day10.a;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public final class GaussianSolver {

    private GaussianSolver() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static int solve(EquationSystem system) {
        return reduce(system, 0, 0);
    }

    private static int reduce(EquationSystem system, int pivot, int col) {
        if (col >= system.cols() - 1 || pivot >= system.rows()) {
            return evaluateMinimumPresses(system);
        }

        return findPivot(system, pivot, col)
                .map(sel -> reduce(system.swapRows(pivot, sel).eliminate(pivot, col), pivot + 1, col + 1))
                .orElseGet(() -> reduce(system, pivot, col + 1));
    }

    private static Optional<Integer> findPivot(EquationSystem system, int startRow, int col) {
        return IntStream.range(startRow, system.rows())
                .filter(i -> system.getValue(i, col) == 1)
                .boxed()
                .findFirst();
    }

    private static int evaluateMinimumPresses(EquationSystem rrefSystem) {
        List<Integer> pivotCols = IntStream.range(0, rrefSystem.rows())
                .map(i -> findFirstOne(rrefSystem, i))
                .filter(c -> c != -1)
                .boxed()
                .toList();

        List<Integer> freeCols = IntStream.range(0, rrefSystem.cols() - 1)
                .filter(c -> !pivotCols.contains(c))
                .boxed()
                .toList();

        return IntStream.range(0, 1 << freeCols.size())
                .map(mask -> calculatePressesForMask(rrefSystem, pivotCols, freeCols, mask))
                .min()
                .orElse(0);
    }

    private static int findFirstOne(EquationSystem system, int row) {
        return IntStream.range(0, system.cols() - 1)
                .filter(c -> system.getValue(row, c) == 1)
                .findFirst()
                .orElse(-1);
    }

    private static int calculatePressesForMask(EquationSystem system, List<Integer> pivotCols, List<Integer> freeCols, int mask) {
        int freePresses = Integer.bitCount(mask);

        int pivotPresses = IntStream.range(0, pivotCols.size())
                .map(i -> calculatePivotValue(system, i, freeCols, mask))
                .sum();

        return freePresses + pivotPresses;
    }

    private static int calculatePivotValue(EquationSystem system, int row, List<Integer> freeCols, int mask) {
        int baseValue = system.getValue(row, system.cols() - 1);

        int dotProduct = IntStream.range(0, freeCols.size())
                .map(idx -> system.getValue(row, freeCols.get(idx)) * ((mask >> idx) & 1))
                .sum();

        return (baseValue + dotProduct) % 2;
    }
}
