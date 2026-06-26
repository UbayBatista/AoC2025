package software.aoc.day08.b;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaygroundTest {

    @Test
    public void should_calculate_product_of_three_largest_circuits_after_k_connections() {
        List<String> input = List.of(
                "162,817,812", "57,618,57", "906,360,560", "592,479,940",
                "352,342,300", "466,668,158", "542,29,236", "431,825,988",
                "739,650,466", "52,470,668", "216,146,977", "819,987,18",
                "117,168,530", "805,96,715", "346,949,466", "970,615,88",
                "941,993,340", "862,61,35", "984,92,344", "425,690,689"
        );

        assertThat(new Playground(input).calculateLargestCircuitsProduct(10, 3)).isEqualTo(40L);
    }

    @Test
    public void should_calculate_x_product_of_final_connection_to_complete_circuit() {
        List<String> input = List.of(
                "162,817,812", "57,618,57", "906,360,560", "592,479,940",
                "352,342,300", "466,668,158", "542,29,236", "431,825,988",
                "739,650,466", "52,470,668", "216,146,977", "819,987,18",
                "117,168,530", "805,96,715", "346,949,466", "970,615,88",
                "941,993,340", "862,61,35", "984,92,344", "425,690,689"
        );

        assertThat(new Playground(input).calculateLastConnectionXProduct()).isEqualTo(25272L);
    }
}
