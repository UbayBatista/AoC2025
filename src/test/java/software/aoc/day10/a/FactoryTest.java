package software.aoc.day10.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FactoryTest {

    @Test
    public void should_calculate_minimum_total_presses_for_entire_factory() {
        List<String> rawInputLines = List.of(
                "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
                "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
                "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"
        );

        assertThat(
                new Factory(
                        rawInputLines.stream()
                                .map(MachineParser::parse)
                                .toList()
                ).calculateMinimumTotalPresses()
        ).isEqualTo(7);
    }
}
