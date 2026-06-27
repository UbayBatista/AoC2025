package software.aoc.day09.a;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TileTest {

    @Test
    public void should_parse_coordinate_string_into_tile() {
        Tile tile = Tile.fromString("11,7");

        assertThat(tile.x()).isEqualTo(11);
        assertThat(tile.y()).isEqualTo(7);
    }

    @Test
    public void should_calculate_correct_area_with_another_tile() {
        Tile tile1 = new Tile(2, 5);
        Tile tile2 = new Tile(9, 7);

        assertThat(tile1.calculateAreaWith(tile2)).isEqualTo(24);
    }

    @Test
    public void should_calculate_correct_area_regardless_of_order() {
        Tile tile1 = new Tile(7, 1);
        Tile tile2 = new Tile(11, 7);

        long area1 = tile1.calculateAreaWith(tile2);
        long area2 = tile2.calculateAreaWith(tile1);

        assertThat(area1).isEqualTo(35);
        assertThat(area2).isEqualTo(35);
        assertThat(area1).isEqualTo(area2);
    }
}
