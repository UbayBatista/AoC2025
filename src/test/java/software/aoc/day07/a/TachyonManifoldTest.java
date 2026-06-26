package software.aoc.day07.a;

import org.junit.jupiter.api.Test;
import java.util.List;
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
    public void should_transmit_freely_through_empty_space() {
        PropagationStep step = new BeamPropagator().advance(Set.of(7), "...............");

        assertThat(step.activeBeams()).containsExactly(7);
        assertThat(step.splitCount()).isZero();
    }

    @Test
    public void should_split_beam_and_increment_counter_when_encountering_splitter() {
        PropagationStep step = new BeamPropagator().advance(Set.of(7), ".......^.......");

        assertThat(step.activeBeams()).containsExactlyInAnyOrder(6, 8);
        assertThat(step.splitCount()).isEqualTo(1L);
    }

    @Test
    public void should_merge_beams_landing_on_same_column() {
        PropagationStep step = new BeamPropagator().advance(Set.of(6, 8), "......^.^......");

        assertThat(step.activeBeams()).containsExactlyInAnyOrder(5, 7, 9);
        assertThat(step.splitCount()).isEqualTo(2L);
    }

    @Test
    public void should_calculate_total_splits_for_example_manifold() {
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

        assertThat(new TachyonManifold(grid).simulate()).isEqualTo(21L);
    }
}
