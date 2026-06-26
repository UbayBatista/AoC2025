package software.aoc.day08.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JunctionBoxTest {

    @Test
    public void should_parse_junction_box_and_calculate_squared_distance() {
        JunctionBox boxA = JunctionBox.parse("162,817,812");
        JunctionBox boxB = JunctionBox.parse("425,690,689");

        assertThat(boxA.x()).isEqualTo(162);
        assertThat(boxA.y()).isEqualTo(817);
        assertThat(boxA.z()).isEqualTo(812);

        assertThat(boxA.distanceSquaredTo(boxB)).isEqualTo(100427L);
    }

    @Test
    public void should_throw_exception_on_invalid_junction_box_format() {
        assertThatThrownBy(() -> JunctionBox.parse("162,817"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid coordinate format");
    }
}
