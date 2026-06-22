package software.aoc.day01.b;

import java.util.stream.IntStream;

public record DialState(int position, int zeroCount) {

    public DialState next(int step) {
        return new DialState(calculateNewPosition(step), zeroCount + countZerosDuring(step));
    }

    private int calculateNewPosition(int step) {
        return normalize(position + step);
    }

    private int countZerosDuring(int step) {
        int direction = step > 0 ? 1 : -1;
        return (int) IntStream.rangeClosed(1, Math.abs(step))
                .map(i -> normalize(position + (i * direction)))
                .filter(pos -> pos == 0)
                .count();
    }

    private int normalize(int value) {
        return ((value % 100) + 100) % 100;
    }
}
