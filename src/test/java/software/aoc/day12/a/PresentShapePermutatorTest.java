package software.aoc.day12.a;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class PresentShapePermutatorTest {

    @Test
    public void should_generate_all_unique_normalized_permutations() {
        Set<Point> blocks = Set.of(
                new Point(0, 0), new Point(1, 0), new Point(2, 0),
                new Point(0, 1)
        );
        Set<PresentShape> permutations = PresentShapePermutator.generatePermutations(new PresentShape(99, blocks));

        assertThat(permutations).hasSize(8);
        assertThat(permutations).allMatch(PresentShape::isNormalized);
    }
}
