package software.aoc.day07.a;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record PropagationStep(Set<Integer> activeBeams, long splitCount) {

    public static PropagationStep empty() {
        return new PropagationStep(Set.of(), 0L);
    }

    public static PropagationStep freeTransmission(int column) {
        return new PropagationStep(Set.of(column), 0L);
    }

    public static PropagationStep splitEvent(int column) {
        return new PropagationStep(Set.of(column - 1, column + 1), 1L);
    }

    public PropagationStep merge(PropagationStep other) {
        return new PropagationStep(combineBeams(other), accumulateSplits(other));
    }

    private Set<Integer> combineBeams(PropagationStep other) {
        return Stream.concat(this.activeBeams().stream(), other.activeBeams().stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    private long accumulateSplits(PropagationStep other) {
        return this.splitCount() + other.splitCount();
    }
}
