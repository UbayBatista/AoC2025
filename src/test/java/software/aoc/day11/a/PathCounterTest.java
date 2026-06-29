package software.aoc.day11.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PathCounterTest {

    @Test
    public void should_count_all_valid_paths_from_start_to_end() {
        DeviceGraph graph = new DeviceGraph(Map.of(
                "you", List.of("bbb", "ccc"),
                "bbb", List.of("ddd", "eee"),
                "ccc", List.of("ddd", "eee", "fff"),
                "ddd", List.of("ggg"),
                "eee", List.of("out"),
                "fff", List.of("out"),
                "ggg", List.of("out"),
                "hhh", List.of("ccc", "fff", "iii"),
                "iii", List.of("out")
        ));

        assertThat(PathCounter.countPaths(graph, "you", "out")).isEqualTo(5L);
    }

    @Test
    public void should_return_zero_when_no_path_exists() {
        DeviceGraph graph = new DeviceGraph(Map.of(
                "you", List.of("bbb"),
                "bbb", List.of("ccc")
        ));

        assertThat(PathCounter.countPaths(graph, "you", "out")).isZero();
    }

    @Test
    public void should_return_zero_when_start_node_is_missing() {
        DeviceGraph graph = new DeviceGraph(Map.of(
                "aaa", List.of("bbb")
        ));

        assertThat(PathCounter.countPaths(graph, "you", "out")).isZero();
    }
}
