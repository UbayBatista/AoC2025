package software.aoc.day12.a;

import java.util.List;

public final class TreeFarmTestData {

    private TreeFarmTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final List<String> EXAMPLE_INPUT = List.of(
            "0:",
            "###",
            "##.",
            "##.",
            "",
            "1:",
            "###",
            "##.",
            ".##",
            "",
            "2:",
            ".##",
            "###",
            "##.",
            "",
            "3:",
            "##.",
            "###",
            "##.",
            "",
            "4:",
            "###",
            "#..",
            "###",
            "",
            "5:",
            "###",
            ".#.",
            "###",
            "",
            "4x4: 0 0 0 0 2 0",
            "12x5: 1 0 1 0 2 2",
            "12x5: 1 0 1 0 3 2"
    );
}
