package software.aoc.day03.a;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class Main {

    private Main() {
        throw new UnsupportedOperationException("Application entry point cannot be instantiated");
    }

    public static void main(String[] args) throws Exception {
        printTotalSum(readInput());
    }

    private static List<String> readInput() throws Exception {
        return Files.readAllLines(inputPath());
    }

    private static Path inputPath() {
        return Paths.get("src/main/resources/day03/input.txt");
    }

    private static void printTotalSum(List<String> input) {
        System.out.println("Result: " + JoltageCalculator.calculateTotalJoltage(input));
    }
}
