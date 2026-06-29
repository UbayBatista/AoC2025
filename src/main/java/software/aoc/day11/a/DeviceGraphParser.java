package software.aoc.day11.a;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class DeviceGraphParser {

    private DeviceGraphParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static DeviceGraph parse(List<String> unparsedConnections) {
        return new DeviceGraph(unparsedConnections.stream()
                .map(connectionDefinition -> connectionDefinition.split(": "))
                .collect(Collectors.toUnmodifiableMap(
                        sourceAndOutputs -> sourceAndOutputs[0],
                        sourceAndOutputs -> parseOutputs(sourceAndOutputs[1])
                )));
    }

    private static List<String> parseOutputs(String rawOutputs) {
        return Arrays.stream(rawOutputs.split(" "))
                .toList();
    }
}
