package software.aoc.day06.a;

import java.util.List;

public record Problem(List<Long> numbers, Operator operator) {

    public long evaluate() {
        return operator.apply(numbers.stream());
    }
}
