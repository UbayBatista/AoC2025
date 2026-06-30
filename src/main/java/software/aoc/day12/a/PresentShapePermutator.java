package software.aoc.day12.a;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PresentShapePermutator {

    private PresentShapePermutator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Set<PresentShape> generatePermutations(PresentShape baseShape) {
        return Stream.iterate(baseShape, PresentShapePermutator::rotateShape)
                .limit(4)
                .flatMap(rotation -> Stream.of(rotation, flipShape(rotation)))
                .map(PresentShape::normalize)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static PresentShape rotateShape(PresentShape shape) {
        return new PresentShape(shape.id(), shape.blocks().stream()
                .map(Point::rotate90)
                .collect(Collectors.toUnmodifiableSet()));
    }

    private static PresentShape flipShape(PresentShape shape) {
        return new PresentShape(shape.id(), shape.blocks().stream()
                .map(Point::flipHorizontal)
                .collect(Collectors.toUnmodifiableSet()));
    }
}
