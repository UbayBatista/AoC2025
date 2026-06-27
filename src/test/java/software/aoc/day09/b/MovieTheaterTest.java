package software.aoc.day09.b;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieTheaterTest {

    private List<Tile> tiles;

    @BeforeEach
    public void setUp() {
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
    }
    @Test
    public void should_find_largest_rectangle_area_from_provided_list() {
        assertThat(new MovieTheater(tiles).calculateLargestRectangleArea()).isEqualTo(50);
    }

    @Test
    public void should_find_largest_valid_rectangle_area_from_provided_list() {
        assertThat(new MovieTheater(tiles).calculateLargestValidRectangleArea()).isEqualTo(24);
    }
}
