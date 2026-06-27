package software.aoc.day09.a;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieTheaterTest {

    @Test
    public void should_find_largest_rectangle_area_from_provided_list() {
        List<Tile> tiles = List.of(
                new Tile(7, 1),
                new Tile(11, 1),
                new Tile(11, 7),
                new Tile(9, 7),
                new Tile(9, 5),
                new Tile(2, 5),
                new Tile(2, 3),
                new Tile(7, 3)
        );

        assertThat(new MovieTheater(tiles).calculateLargestRectangleArea()).isEqualTo(50);
    }
}
