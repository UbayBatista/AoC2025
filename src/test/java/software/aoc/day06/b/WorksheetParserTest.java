package software.aoc.day06.b;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class WorksheetParserTest {

    @Test
    public void should_parse_raw_input_into_vertical_problems() {
        List<String> rawInput = List.of(
                "123 328  51 64 ",
                " 45 64  387 23 ",
                "  6 98  215 314",
                "* +   * +  "
        );
        List<Problem> problems = WorksheetParser.parse(rawInput);

        assertThat(problems).hasSize(4);
        assertThat(problems.get(0).evaluate()).isEqualTo(8544L);
        assertThat(problems.get(1).evaluate()).isEqualTo(625L);
        assertThat(problems.get(2).evaluate()).isEqualTo(3253600L);
        assertThat(problems.get(3).evaluate()).isEqualTo(1058L);
    }
}
