package software.aoc.day11.a;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record DeviceGraph(Map<String, List<String>> connections) {

    public List<String> getOutputs(String device) {
        return connections.getOrDefault(device, Collections.emptyList());
    }
}
