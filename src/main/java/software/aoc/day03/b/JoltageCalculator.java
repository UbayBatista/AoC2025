package software.aoc.day03.b;

import java.util.List;

public final class JoltageCalculator {

    private JoltageCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static long calculateTotalJoltage(List<String> banks) {
        return banks.stream()
                .map(BatteryBank::new)
                .mapToLong(BatteryBank::maxJoltage)
                .sum();
    }

    public static long calculateMaxJoltage(String bankData) {
        return new BatteryBank(bankData).maxJoltage();
    }
}
