package software.aoc.day03.b;

import java.util.stream.Stream;

public record MaxSequenceSelector(int targetLength) {

    public String extractFrom(String source) {
        return Stream.iterate(new SelectionState(source, targetLength, ""), SelectionState::advance)
                .skip(targetLength)
                .findFirst()
                .map(SelectionState::selected)
                .orElseThrow(() -> new IllegalStateException("Failed to extract sequence"));
    }
}
