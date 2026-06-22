package software.aoc.day01.a;

import java.util.List;

public class SafeDial {
    private static final int INITIAL_POSITION = 50;

    public int calculatePassword(List<String> instructions) {
        return performRotations(instructions).zeroCount();
    }

    private DialState performRotations(List<String> instructions) {
        return instructions.stream()
                .map(RotationParser::parse)
                .reduce(initialState(), DialState::next, (s1, _) -> s1);
    }

    private DialState initialState() {
        return new DialState(INITIAL_POSITION, 0);
    }
}