package software.aoc.day11.b;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ConstrainedPathCounterTest {

    @Test
    public void should_count_only_paths_visiting_both_dac_and_fft() {
        DeviceGraph graph = new DeviceGraph(Map.ofEntries(
                Map.entry("svr", List.of("aaa", "bbb")),
                Map.entry("aaa", List.of("fft")),
                Map.entry("fft", List.of("ccc")),
                Map.entry("bbb", List.of("tty")),
                Map.entry("tty", List.of("ccc")),
                Map.entry("ccc", List.of("ddd", "eee")),
                Map.entry("ddd", List.of("hub")),
                Map.entry("hub", List.of("fff")),
                Map.entry("eee", List.of("dac")),
                Map.entry("dac", List.of("fff")),
                Map.entry("fff", List.of("ggg", "hhh")),
                Map.entry("ggg", List.of("out")),
                Map.entry("hhh", List.of("out"))
        ));

        assertThat(ConstrainedPathCounter.countPaths(graph, "svr", "out", "dac", "fft")).isEqualTo(2L);
    }

    @Test
    public void should_return_zero_when_path_misses_required_nodes() {
        DeviceGraph graph = new DeviceGraph(Map.of(
                "svr", List.of("aaa"),
                "aaa", List.of("out")
        ));

        assertThat(ConstrainedPathCounter.countPaths(graph, "svr", "out", "dac", "fft")).isZero();
    }

    @Test
    public void should_return_zero_when_path_visits_required_nodes_but_misses_target() {
        DeviceGraph graph = new DeviceGraph(Map.of(
                "svr", List.of("dac"),
                "dac", List.of("fft"),
                "fft", List.of("dead_end")
        ));

        assertThat(ConstrainedPathCounter.countPaths(graph, "svr", "out", "dac", "fft")).isZero();
    }
}
