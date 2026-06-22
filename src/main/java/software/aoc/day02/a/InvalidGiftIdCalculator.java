package software.aoc.day02.a;

import java.util.Arrays;

public final class InvalidGiftIdCalculator {

    private InvalidGiftIdCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long calculateSumOfInvalidIds(String input) {
        return Arrays.stream(input.split(","))
                .map(Range::fromString)
                .flatMapToLong(Range::stream)
                .mapToObj(GiftId::new)
                .filter(GiftId::isInvalid)
                .mapToLong(GiftId::value)
                .sum();
    }

    public static boolean isInvalidId(long id) {
        return new GiftId(id).isInvalid();
    }
}
