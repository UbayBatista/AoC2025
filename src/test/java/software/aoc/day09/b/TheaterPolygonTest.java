package software.aoc.day09.b;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class TheaterPolygonTest {

    private TheaterPolygon polygon;

    @BeforeEach
    public void setUp() {
        List<Tile> vertices = List.of(
                new Tile(7, 1),
                new Tile(11, 1),
                new Tile(11, 7),
                new Tile(9, 7),
                new Tile(9, 5),
                new Tile(2, 5),
                new Tile(2, 3),
                new Tile(7, 3)
        );
        polygon = new TheaterPolygon(vertices);
    }

    @Test
    public void should_return_true_when_rectangle_is_fully_inside_polygon() {
        assertThat(polygon.containsRectangle(new Tile(9, 5), new Tile(2, 3))).isTrue();
    }

    @Test
    public void should_return_false_when_rectangle_is_not_fully_inside_polygon() {
        assertThat(polygon.containsRectangle(new Tile(7, 1), new Tile(11, 7))).isFalse();
    }
}
