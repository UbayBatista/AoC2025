package software.aoc.day02.b;

import java.util.Arrays;
import java.util.stream.Stream;

public final class InvalidGiftIdCalculator {

    private InvalidGiftIdCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long calculateSumOfInvalidIds(String input) {
        return parseRanges(input)
                .flatMapToLong(Range::stream)
                .mapToObj(GiftId::new)
                .filter(GiftId::isInvalid)
                .mapToLong(GiftId::value)
                .sum();
    }

    private static Stream<Range> parseRanges(String input) {
        return Arrays.stream(input.replaceAll("\\s+", "").split(","))
                .map(Range::fromString);
    }

    public static boolean isInvalidId(long id) {
        return new GiftId(id).isInvalid();
    }
}
