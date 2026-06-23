package software.aoc.day03.b;

import java.util.stream.IntStream;

public record SelectionState(String remaining, int needed, String selected) {

    public SelectionState advance() {
        int maxIndex = findIndexOfMaxDigitInWindow();
        return new SelectionState(
                remaining.substring(maxIndex + 1),
                needed - 1,
                selected + remaining.charAt(maxIndex)
        );
    }

    private int findIndexOfMaxDigitInWindow() {
        return IntStream.rangeClosed(0, remaining.length() - needed)
                .reduce(this::keepMaxDigitIndex)
                .orElseThrow(() -> new IllegalStateException("Consistency error in search window"));
    }

    private int keepMaxDigitIndex(int index1, int index2) {
        return remaining.charAt(index1) >= remaining.charAt(index2) ? index1 : index2;
    }
}
