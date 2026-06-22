package software.aoc.day01.a;

public class RotationParser {
    public static int parse(String instruction) {
        return signOf(instruction) * valueOf(instruction);
    }

    private static int signOf(String instruction) {
        return instruction.startsWith("L") ? -1 : 1;
    }

    private static int valueOf(String instruction) {
        return Integer.parseInt(instruction.substring(1));
    }
}
