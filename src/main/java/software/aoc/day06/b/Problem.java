package software.aoc.day06.b;

import java.util.List;

public record Problem(List<Long> numbers, Operator operator) {

    public long evaluate() {
        return operator.apply(numbers.stream());
    }
}
