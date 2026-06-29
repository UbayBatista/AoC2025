package software.aoc.day10.b;

import java.util.Arrays;
import java.util.stream.IntStream;

public record LinearSystem(double[][] matrix) {

    public LinearSystem {
        matrix = cloneMatrix(matrix);
    }

    public static LinearSystem from(Machine machine) {
        int rows = machine.targetJoltages().size();
        int cols = machine.buttons().size();
        double[][] buildMatrix = new double[rows][cols + 1];

        IntStream.range(0, cols).forEach(j ->
                machine.buttons().get(j).affectedCounters()
                        .forEach(counter -> buildMatrix[counter][j] = 1.0));

        IntStream.range(0, rows).forEach(i ->
                buildMatrix[i][cols] = machine.targetJoltages().get(i));

        return new LinearSystem(buildMatrix);
    }

    public int rows() { return matrix.length; }
    public int cols() { return matrix[0].length; }
    public double getValue(int row, int col) { return matrix[row][col]; }

    public LinearSystem toReducedRowEchelonForm() {
        return reduceRecursive(0, 0);
    }

    private LinearSystem reduceRecursive(int pivotRow, int col) {
        if (col >= cols() - 1 || pivotRow >= rows()) {
            return this;
        }

        int maxRow = findMaxAbsoluteRow(col, pivotRow);

        if (Math.abs(getValue(maxRow, col)) < 1e-9) {
            return reduceRecursive(pivotRow, col + 1);
        }

        return this.swapRows(pivotRow, maxRow)
                .normalizeRow(pivotRow, col)
                .eliminateColumn(pivotRow, col)
                .reduceRecursive(pivotRow + 1, col + 1);
    }

    private int findMaxAbsoluteRow(int col, int startRow) {
        return IntStream.range(startRow, rows())
                .boxed()
                .max((r1, r2) -> Double.compare(Math.abs(matrix[r1][col]), Math.abs(matrix[r2][col])))
                .orElse(startRow);
    }

    private LinearSystem swapRows(int r1, int r2) {
        double[][] nextMatrix = cloneMatrix(matrix);
        double[] temp = nextMatrix[r1];
        nextMatrix[r1] = nextMatrix[r2];
        nextMatrix[r2] = temp;
        return new LinearSystem(nextMatrix);
    }

    private LinearSystem normalizeRow(int row, int col) {
        double[][] nextMatrix = cloneMatrix(matrix);
        double pivot = nextMatrix[row][col];
        IntStream.range(col, cols()).forEach(j -> nextMatrix[row][j] /= pivot);
        return new LinearSystem(nextMatrix);
    }

    private LinearSystem eliminateColumn(int pivotRow, int col) {
        double[][] nextMatrix = IntStream.range(0, rows())
                .mapToObj(i -> applyEliminationIfNecessary(i, pivotRow, col))
                .toArray(double[][]::new);
        return new LinearSystem(nextMatrix);
    }

    private double[] applyEliminationIfNecessary(int currentRow, int pivotRow, int col) {
        double[] newRow = matrix[currentRow].clone();
        if (currentRow == pivotRow) return newRow;

        double factor = newRow[col];
        IntStream.range(col, cols()).forEach(j -> newRow[j] -= factor * matrix[pivotRow][j]);
        return newRow;
    }

    private static double[][] cloneMatrix(double[][] source) {
        return Arrays.stream(source).map(double[]::clone).toArray(double[][]::new);
    }
}
