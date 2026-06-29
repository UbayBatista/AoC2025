package software.aoc.day11.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class DeviceGraphParserTest {

    @Test
    public void should_parse_input_lines_into_directed_graph() {
        List<String> input = List.of(
                "aaa: you hhh",
                "you: bbb ccc",
                "eee: out"
        );

        assertThat(DeviceGraphParser.parse(input).connections())
                .containsEntry("aaa", List.of("you", "hhh"))
                .containsEntry("you", List.of("bbb", "ccc"))
                .containsEntry("eee", List.of("out"));
    }
}
