package software.aoc.day10.b;

import java.util.List;

public record Button(List<Integer> affectedCounters) {
    public Button {
        affectedCounters = List.copyOf(affectedCounters);
    }
}
