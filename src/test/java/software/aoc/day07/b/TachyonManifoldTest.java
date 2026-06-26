package software.aoc.day07.b;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TachyonManifoldTest {

    @Test
    public void should_locate_starting_position() {
        List<String> grid = List.of(
                ".......S.......",
                "..............."
        );

        assertThat(new TachyonManifold(grid).getStartColumn()).isEqualTo(7);
    }

    @Test
    public void should_transmit_freely_through_empty_space_preserving_timelines() {
        assertThat(new BeamPropagator().advance(Map.of(7, 1L), "...............").activeTimelines()).containsExactly(Map.entry(7, 1L));
    }

    @Test
    public void should_split_into_two_timelines_when_encountering_splitter() {
        assertThat(new BeamPropagator().advance(Map.of(7, 1L), ".......^.......").activeTimelines()).containsExactlyInAnyOrderEntriesOf(Map.of(
                6, 1L,
                8, 1L
        ));
    }

    @Test
    public void should_accumulate_timelines_when_converging_on_same_column() {
        assertThat(new BeamPropagator().advance(Map.of(6, 1L, 8, 1L), "......^.^......").activeTimelines()).containsExactlyInAnyOrderEntriesOf(Map.of(
                5, 1L,
                7, 2L,
                9, 1L
        ));
    }

    @Test
    public void should_calculate_total_active_timelines_for_quantum_manifold() {
        List<String> grid = List.of(
                ".......S.......",
                "...............",
                ".......^.......",
                "...............",
                "......^.^......",
                "...............",
                ".....^.^.^.....",
                "...............",
                "....^.^...^....",
                "...............",
                "...^.^...^.^...",
                "...............",
                "..^...^.....^..",
                "...............",
                ".^.^.^.^.^...^.",
                "..............."
        );

        assertThat(new TachyonManifold(grid).simulate()).isEqualTo(40L);
    }
}
