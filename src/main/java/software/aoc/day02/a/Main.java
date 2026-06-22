package software.aoc.day02.a;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

    private Main() {
        throw new UnsupportedOperationException("Application entry point cannot be instantiated");
    }

    public static void main(String[] args) throws Exception {
        printTotalSum(readInput());
    }

    private static String readInput() throws Exception {
        return Files.readString(inputPath()).trim();
    }

    private static Path inputPath() {
        return Paths.get("src/main/resources/day02/input.txt");
    }

    private static void printTotalSum(String input) {
        System.out.println("Result: " + InvalidGiftIdCalculator.calculateSumOfInvalidIds(input));
    }
}
