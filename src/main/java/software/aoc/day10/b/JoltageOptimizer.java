package software.aoc.day10.b;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class JoltageOptimizer {

    private record MathContext(LinearSystem rref, List<Integer> freeCols, Map<Integer, Integer> pivotMap, List<Integer> maxBounds) {}

    private JoltageOptimizer() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int findMinimumPresses(Machine machine) {
        LinearSystem rref = LinearSystem.from(machine).toReducedRowEchelonForm();
        MathContext ctx = buildContext(machine, rref);

        return evaluateNullSpace(0, List.of(), ctx);
    }

    private static MathContext buildContext(Machine machine, LinearSystem rref) {
        List<Integer> pivotCols = findPivotColumns(rref);
        return new MathContext(
                rref,
                findFreeColumns(rref, pivotCols),
                mapPivotToRow(rref, pivotCols),
                calculateMaxBounds(machine)
        );
    }

    private static int evaluateNullSpace(int freeIdx, List<Integer> currentVals, MathContext ctx) {
        if (freeIdx == ctx.freeCols().size()) {
            return validateAndCountPresses(currentVals, ctx).orElse(Integer.MAX_VALUE);
        }

        int currentFreeCol = ctx.freeCols().get(freeIdx);
        int maxBound = ctx.maxBounds().get(currentFreeCol);

        return IntStream.rangeClosed(0, maxBound)
                .map(val -> evaluateNullSpace(freeIdx + 1, appendValue(currentVals, val), ctx))
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    private static List<Integer> appendValue(List<Integer> list, int value) {
        return Stream.concat(list.stream(), Stream.of(value)).toList();
    }

    private static Optional<Integer> validateAndCountPresses(List<Integer> freeVals, MathContext ctx) {
        int freePresses = freeVals.stream().mapToInt(Integer::intValue).sum();

        return ctx.pivotMap().values().stream()
                .map(pRow -> extractValidPivotPresses(pRow, freeVals, ctx))
                .reduce(JoltageOptimizer::combineOptionals)
                .orElse(Optional.of(0))
                .map(pivotPresses -> freePresses + pivotPresses);
    }

    private static Optional<Integer> combineOptionals(Optional<Integer> a, Optional<Integer> b) {
        if (a.isEmpty() || b.isEmpty()) return Optional.empty();
        return Optional.of(a.get() + b.get());
    }

    private static Optional<Integer> extractValidPivotPresses(int pRow, List<Integer> freeVals, MathContext ctx) {
        double baseVal = ctx.rref().getValue(pRow, ctx.rref().cols() - 1);

        double dotProduct = IntStream.range(0, ctx.freeCols().size())
                .mapToDouble(i -> ctx.rref().getValue(pRow, ctx.freeCols().get(i)) * freeVals.get(i))
                .sum();

        double finalVal = baseVal - dotProduct;
        long rounded = Math.round(finalVal);

        return (Math.abs(finalVal - rounded) > 1e-5 || rounded < 0)
                ? Optional.empty()
                : Optional.of((int) rounded);
    }

    private static List<Integer> findPivotColumns(LinearSystem rref) {
        return IntStream.range(0, rref.cols() - 1)
                .filter(col -> isPivotColumn(rref, col))
                .boxed()
                .toList();
    }

    private static List<Integer> findFreeColumns(LinearSystem rref, List<Integer> pivotCols) {
        return IntStream.range(0, rref.cols() - 1)
                .filter(col -> !pivotCols.contains(col))
                .boxed()
                .toList();
    }

    private static boolean isPivotColumn(LinearSystem rref, int col) {
        return IntStream.range(0, rref.rows())
                .filter(i -> Math.abs(rref.getValue(i, col) - 1.0) < 1e-9)
                .anyMatch(i -> hasLeadingOne(rref, i, col) && isOnlyNonZeroInColumn(rref, i, col));
    }

    private static boolean hasLeadingOne(LinearSystem rref, int row, int col) {
        return IntStream.range(0, col).allMatch(j -> Math.abs(rref.getValue(row, j)) < 1e-9);
    }

    private static boolean isOnlyNonZeroInColumn(LinearSystem rref, int row, int col) {
        return IntStream.range(0, rref.rows()).allMatch(k -> k == row || Math.abs(rref.getValue(k, col)) < 1e-9);
    }

    private static Map<Integer, Integer> mapPivotToRow(LinearSystem rref, List<Integer> pivotCols) {
        return pivotCols.stream().collect(Collectors.toMap(
                col -> col,
                col -> IntStream.range(0, rref.rows())
                        .filter(i -> Math.abs(rref.getValue(i, col) - 1.0) < 1e-9)
                        .findFirst().orElseThrow()
        ));
    }

    private static List<Integer> calculateMaxBounds(Machine machine) {
        return IntStream.range(0, machine.buttons().size())
                .mapToObj(j -> machine.buttons().get(j).affectedCounters().stream()
                        .mapToInt(idx -> machine.targetJoltages().get(idx))
                        .min().orElse(0))
                .toList();
    }
}