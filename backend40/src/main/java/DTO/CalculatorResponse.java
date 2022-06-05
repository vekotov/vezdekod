package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CalculatorResponse {
    private Double sumLatency;
    private Double minLatency;
    private Double maxLatency;
    private Double avgLatency;

    private Double percentile10;
    private Double percentile25;
    private Double percentile50;
    private Double percentile75;
    private Double percentile90;
    private Double percentile95;
    private Double percentile99;

    private Map<Integer, Long> codes;
}
