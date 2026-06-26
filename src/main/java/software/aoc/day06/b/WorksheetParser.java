package software.aoc.day06.b;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
                .allMatch(line -> getSafeChar(line, col) == ' ');
    }

    private static Problem parseBlock(List<String> rawInput, int start, int end) {
        return new Problem(
                extractNumbersVertical(rawInput, start, end),
                extractOperator(rawInput.get(rawInput.size() - 1), start, end));
    }

    private static List<Long> extractNumbersVertical(List<String> rawInput, int start, int end) {
        return IntStream.iterate(end - 1, col -> col >= start, col -> col - 1)
                .mapToObj(col -> extractColumnNumber(rawInput, col))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Optional<Long> extractColumnNumber(List<String> rawInput, int col) {
        String numberStr = IntStream.range(0, rawInput.size() - 1)
                .mapToObj(row -> getSafeChar(rawInput.get(row), col))
                .filter(c -> c != ' ')
                .map(String::valueOf)
                .collect(Collectors.joining());

        return numberStr.isEmpty() ? Optional.empty() : Optional.of(Long.parseLong(numberStr));
    }

    private static Operator extractOperator(String lastLine, int start, int end) {
        return Operator.fromSymbol(IntStream.range(start, end)
                .mapToObj(col -> getSafeChar(lastLine, col))
                .filter(c -> c != ' ')
                .map(String::valueOf)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No operator found in block")));
    }

    private static char getSafeChar(String line, int col) {
        return col < line.length() ? line.charAt(col) : ' ';
    }
}
