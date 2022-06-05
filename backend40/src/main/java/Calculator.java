import DTO.CalculatorArguments;
import DTO.CalculatorResponse;
import DTO.ThreadResponse;

import java.util.*;

public class Calculator {
    public Calculator() {
    }

    public CalculatorResponse calculate(CalculatorArguments args) {
        List<Double> allLatencies = new ArrayList<>();
        List<ThreadResponse> input = args.getInput();
        Map<Integer, Long> codes = new HashMap<>();

        for (ThreadResponse response : input) {
            allLatencies.addAll(response.getLatencyList());
            response.getCodes().forEach((k, v) -> {
                codes.compute(k, (k2, v2) -> {
                   if (v2 == null) return v;
                   return v2 + v;
                });
            });
        }

        allLatencies.sort(Comparator.naturalOrder());

        Double min = null;
        Double max = null;
        Double sum = 0.0;
        for (Double latency : allLatencies) {
            if (min == null) min = latency;
            if (max == null) max = latency;
            sum += latency;

            min = Math.min(min, latency);
            max = Math.max(max, latency);
        }

        return new CalculatorResponse(
                sum,
                min,
                max,
                sum / allLatencies.size(),

                percentile(allLatencies, 10),
                percentile(allLatencies, 25),
                percentile(allLatencies, 50),
                percentile(allLatencies, 75),
                percentile(allLatencies, 90),
                percentile(allLatencies, 95),
                percentile(allLatencies, 99),

                codes
        );
    }

    private static double percentile(List<Double> latencies, double percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index-1);
    }
}
