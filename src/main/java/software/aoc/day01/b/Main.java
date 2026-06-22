package software.aoc.day01.b;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    private Main() {
        throw new UnsupportedOperationException("Application entry point cannot be instantiated");
    }

    public static void main(String[] args) throws Exception {
        printPassword(readInstructions());
    }

    private static List<String> readInstructions() throws Exception {
        return Files.readAllLines(inputPath());
    }

    private static Path inputPath() {
        return Paths.get("src/main/resources/day01/input.txt");
    }

    private static void printPassword(List<String> instructions) {
        System.out.println("The actual password is: " + new SafeDial().calculatePassword(instructions));
    }
}
