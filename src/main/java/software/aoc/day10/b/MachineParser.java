package software.aoc.day10.b;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class MachineParser {

    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\((.*?)\\)");
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{(.*?)\\}");

    private MachineParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Machine parse(String line) {
        return new Machine(extractButtons(line), extractTargetJoltages(line));
    }

    private static List<Button> extractButtons(String line) {
        return BUTTON_PATTERN.matcher(line).results()
                .map(MatchResult::group)
                .map(s -> s.substring(1, s.length() - 1))
                .map(MachineParser::parseIndices)
                .map(Button::new)
                .toList();
    }

    private static List<Integer> extractTargetJoltages(String line) {
        return JOLTAGE_PATTERN.matcher(line).results()
                .map(MatchResult::group)
                .map(s -> s.substring(1, s.length() - 1))
                .map(MachineParser::parseIndices)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid joltage format"));
    }

    private static List<Integer> parseIndices(String data) {
        return Arrays.stream(data.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}
