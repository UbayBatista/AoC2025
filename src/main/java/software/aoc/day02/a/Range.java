package software.aoc.day02.a;

import java.util.stream.LongStream;

public record Range(long start, long end) {

    public static Range fromString(String rangeString) {
        return fromBoundaries(rangeString.split("-"));
    }

    private static Range fromBoundaries(String[] boundaries) {
        return new Range(Long.parseLong(boundaries[0]), Long.parseLong(boundaries[1]));
    }

    public LongStream stream() {
        return LongStream.rangeClosed(start, end);
    }
}
