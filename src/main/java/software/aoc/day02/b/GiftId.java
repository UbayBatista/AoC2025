package software.aoc.day02.b;

import java.util.stream.IntStream;

public record GiftId(long value) {

    public boolean isInvalid() {
        return isInvalidString(String.valueOf(value));
    }

    private boolean isInvalidString(String idStr) {
        int length = idStr.length();
        return IntStream.rangeClosed(1, length / 2)
                .filter(subLen -> isExactDivisor(length, subLen))
                .anyMatch(subLen -> isRepeatedPattern(idStr, subLen));
    }

    private boolean isExactDivisor(int totalLength, int subLength) {
        return totalLength % subLength == 0;
    }

    private boolean isRepeatedPattern(String s, int subLen) {
        String pattern = s.substring(0, subLen);
        int repetitions = s.length() / subLen;
        return pattern.repeat(repetitions).equals(s);
    }
}
