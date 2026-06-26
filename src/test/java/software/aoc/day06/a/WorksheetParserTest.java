package software.aoc.day06.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class WorksheetParserTest {

    @Test
    public void should_parse_raw_input_into_problems() {
        List<String> rawInput = List.of(
                "123 328 ",
                " 45 64  ",
                "  6 98  ",
                "*   +   "
        );
        List<Problem> problems = WorksheetParser.parse(rawInput);

        assertThat(problems).hasSize(2);
        assertThat(problems.get(0).evaluate()).isEqualTo(33210L);
        assertThat(problems.get(1).evaluate()).isEqualTo(490L);
    }
}
