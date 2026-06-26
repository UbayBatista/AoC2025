package software.aoc.day06.a;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public final class WorksheetParser {

    private WorksheetParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static List<Problem> parse(List<String> rawInput) {
        int width = rawInput.get(0).length();
        return IntStream.range(0, width)
                .filter(col -> isStartOfBlock(rawInput, col))
                .mapToObj(start -> parseBlock(rawInput, start, findNextEmptyColumn(rawInput, start, width)))
                .toList();
    }

    private static boolean isStartOfBlock(List<String> rawInput, int col) {
        return !isColumnEmpty(rawInput, col) && (col == 0 || isColumnEmpty(rawInput, col - 1));
    }

    private static int findNextEmptyColumn(List<String> rawInput, int current, int width) {
        return IntStream.range(current, width)
                .filter(col -> isColumnEmpty(rawInput, col))
                .findFirst()
                .orElse(width);
    }

    private static boolean isColumnEmpty(List<String> rawInput, int col) {
        return rawInput.stream()
                .allMatch(line -> line.length() <= col || line.charAt(col) == ' ');
    }

    private static Problem parseBlock(List<String> rawInput, int start, int end) {
        List<String> blockLines = extractBlockLines(rawInput, start, end);
        return new Problem(extractNumbers(blockLines), extractOperator(blockLines));
    }

    private static List<String> extractBlockLines(List<String> rawInput, int start, int end) {
        return rawInput.stream()
                .map(line -> extractSafely(line, start, end).trim())
                .filter(Predicate.not(String::isEmpty))
                .toList();
    }

    private static List<Long> extractNumbers(List<String> blockLines) {
        return blockLines.stream()
                .limit(blockLines.size() - 1L)
                .map(Long::parseLong)
                .toList();
    }

    private static Operator extractOperator(List<String> blockLines) {
        return Operator.fromSymbol(blockLines.get(blockLines.size() - 1));
    }

    private static String extractSafely(String line, int start, int end) {
        if (start >= line.length()) return "";
        return line.substring(start, Math.min(end, line.length()));
    }
}
