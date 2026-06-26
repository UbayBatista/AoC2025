package software.aoc.day08.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CircuitTrackerTest {

    @Test
    public void should_merge_circuits_and_track_sizes_correctly() {
        CircuitTracker tracker = new CircuitTracker(5);

        assertThat(tracker.getLargestCircuitSizes(3)).containsExactly(1, 1, 1);

        tracker.union(0, 1);
        assertThat(tracker.getLargestCircuitSizes(1)).containsExactly(2);

        tracker.union(2, 3);
        assertThat(tracker.getLargestCircuitSizes(2)).containsExactly(2, 2);

        tracker.union(1, 2);
        assertThat(tracker.getLargestCircuitSizes(2)).containsExactly(4, 1);

        tracker.union(0, 3);
        assertThat(tracker.getLargestCircuitSizes(2)).containsExactly(4, 1);
    }
}
