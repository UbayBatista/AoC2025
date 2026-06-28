package software.aoc.day10.a;

import java.util.Arrays;
import java.util.stream.IntStream;

public record EquationSystem(int[][] matrix) {

    public EquationSystem {
        matrix = cloneMatrix(matrix);
    }

    public static EquationSystem from(Machine machine) {
        int rows = machine.targetState().length();
        int cols = machine.buttons().size();
        int[][] buildMatrix = new int[rows][cols + 1];

        IntStream.range(0, cols).forEach(j ->
                machine.buttons().get(j).toggledLightIndices()
                        .forEach(light -> buildMatrix[light][j] = 1));

        IntStream.range(0, rows).forEach(i ->
                buildMatrix[i][cols] = (machine.targetState().charAt(i) == '#') ? 1 : 0);

        return new EquationSystem(buildMatrix);
    }

    public int rows() { return matrix.length; }
    public int cols() { return matrix[0].length; }
    public int getValue(int row, int col) { return matrix[row][col]; }

    public EquationSystem swapRows(int r1, int r2) {
        int[][] newMatrix = cloneMatrix(matrix);
        int[] temp = newMatrix[r1];
        newMatrix[r1] = newMatrix[r2];
        newMatrix[r2] = temp;
        return new EquationSystem(newMatrix);
    }

    public EquationSystem eliminate(int pivotRow, int col) {
        int[][] newMatrix = IntStream.range(0, rows())
                .mapToObj(i -> applyXorIfApplicable(i, pivotRow, col))
                .toArray(int[][]::new);
        return new EquationSystem(newMatrix);
    }

    private int[] applyXorIfApplicable(int currentRow, int pivotRow, int col) {
        int[] newRow = matrix[currentRow].clone();
        if (currentRow != pivotRow && matrix[currentRow][col] == 1) {
            IntStream.range(col, cols()).forEach(j -> newRow[j] ^= matrix[pivotRow][j]);
        }
        return newRow;
    }

    private static int[][] cloneMatrix(int[][] source) {
        return Arrays.stream(source).map(int[]::clone).toArray(int[][]::new);
    }
}