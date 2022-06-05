package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ThreadResponse {
    private List<Double> latencyList;
    private Map<Integer, Long> codes;
}
