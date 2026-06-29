package software.aoc.day10.b;

import java.util.List;

public record Machine(List<Button> buttons, List<Integer> targetJoltages) {
    public Machine {
        buttons = List.copyOf(buttons);
        targetJoltages = List.copyOf(targetJoltages);
    }
}
