package software.aoc.day10.a;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class MachineParser {

    private static final Pattern TARGET_PATTERN = Pattern.compile("\\[(.*?)\\]");
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\((.*?)\\)");

    private MachineParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Machine parse(String line) {
        return new Machine(extractTargetState(line), extractButtons(line));
    }

    private static String extractTargetState(String line) {
        return TARGET_PATTERN.matcher(line).results()
                .map(MatchResult::group)
                .map(s -> s.substring(1, s.length() - 1))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid target state format"));
    }

    private static List<Button> extractButtons(String line) {
        return BUTTON_PATTERN.matcher(line).results()
                .map(MatchResult::group)
                .map(s -> s.substring(1, s.length() - 1))
                .map(MachineParser::parseIndices)
                .map(Button::new)
                .toList();
    }

    private static List<Integer> parseIndices(String data) {
        return Arrays.stream(data.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}
