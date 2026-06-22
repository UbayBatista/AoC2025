package software.aoc.day01.a;

public record DialState(int position, int zeroCount) {

    public DialState next(int step) {
        return new DialState(normalize(position + step), updatedZeroCount(step));
    }

    private int updatedZeroCount(int step) {
        return normalize(position + step) == 0 ? zeroCount + 1 : zeroCount;
    }

    private int normalize(int value) {
        return ((value % 100) + 100) % 100;
    }
}
