package software.aoc.day10.a;

import java.util.List;

public record Machine(String targetState, List<Button> buttons) {

    public Machine {
        buttons = List.copyOf(buttons);
    }
}
