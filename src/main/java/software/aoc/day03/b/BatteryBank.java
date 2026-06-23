package software.aoc.day03.b;

import java.util.Optional;

public record BatteryBank(String digits) {

    private static final int TARGET_LENGTH = 12;

    public BatteryBank {
        Optional.ofNullable(digits)
                .filter(d -> d.length() >= TARGET_LENGTH)
                .orElseThrow(() -> new IllegalArgumentException("The battery bank must contain at least twelve elements"));
    }

    public long maxJoltage() {
        return Long.parseLong(new MaxSequenceSelector(TARGET_LENGTH).extractFrom(digits));
    }
}
