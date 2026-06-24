package software.aoc.day05.b;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class Main {

    private Main() {
        throw new UnsupportedOperationException("Application entry point cannot be instantiated");
    }

    public static void main(String[] args) throws Exception {
        printTotalFreshIngredients(readInput());
    }

    private static List<String> readInput() throws Exception {
        return Files.readAllLines(inputPath());
    }

    private static Path inputPath() {
        return Paths.get("src/main/resources/day05/input.txt");
    }

    private static void printTotalFreshIngredients(List<String> input) {
        System.out.println("Result: " + TotalFreshIngredientCounter.countTotal(InventoryParser.parseRanges(input)));
    }
}
