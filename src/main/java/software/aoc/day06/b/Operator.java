package software.aoc.day06.b;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Operator {

    ADD("+") {
        @Override
        public long apply(Stream<Long> numbers) {
            return numbers.reduce(0L, Long::sum);
        }
    },
    MULTIPLY("*") {
        @Override
        public long apply(Stream<Long> numbers) {
            return numbers.reduce(1L, (a, b) -> a * b);
        }
    };

    private final String symbol;

    private Operator(String symbol) {
        this.symbol = symbol;
    }

    public abstract long apply(Stream<Long> numbers);

    public static Operator fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported operator: " + symbol));
    }
}
