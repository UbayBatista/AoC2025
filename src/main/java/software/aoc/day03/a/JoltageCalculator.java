package software.aoc.day03.a;

import java.util.List;
import java.util.stream.IntStream;

public final class JoltageCalculator {

    private JoltageCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static int calculateTotalJoltage(List<String> banks) {
        return banks.stream()
                .mapToInt(JoltageCalculator::calculateMaxJoltage)
                .sum();
    }

    public static int calculateMaxJoltage(String bankData) {
        validateBank(bankData);
        return IntStream.range(0, bankData.length() - 1)
                .flatMap(i -> IntStream.range(i + 1, bankData.length())
                        .map(j -> computeJoltageForPair(bankData, i, j)))
                .max()
                .orElseThrow(() -> new IllegalStateException("The voltage could not be calculated"));
    }

    private static void validateBank(String bankData) {
        if (bankData == null || bankData.length() < 2) {
            throw new IllegalArgumentException("The battery bank must contain at least two elements.");
        }
    }

    private static int computeJoltageForPair(String bankData, int firstIndex, int secondIndex) {
        return (Character.getNumericValue(bankData.charAt(firstIndex)) * 10) + Character.getNumericValue(bankData.charAt(secondIndex));
    }
}
