package software.aoc.day10.a;

import java.util.List;

public record Button(List<Integer> toggledLightIndices) {

    public Button {
        toggledLightIndices = List.copyOf(toggledLightIndices);
    }
}
