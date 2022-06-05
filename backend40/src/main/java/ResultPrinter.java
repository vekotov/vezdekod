import DTO.CalculatorResponse;

import java.util.Locale;

public class ResultPrinter {
    public ResultPrinter() {
    }

    public void printResults(CalculatorResponse arg) {
        Locale.setDefault(Locale.US);
        System.out.println("Summary:\n" +
                "\n" +
                " Total: " + String.format("%.5f", arg.getSumLatency()) + " secs\n" +
                "\n" +
                " Slowest: " + String.format("%.5f", arg.getMaxLatency()) + " secs\n" +
                "\n" +
                " Fastest: " + String.format("%.5f", arg.getMinLatency()) + " secs\n" +
                "\n" +
                " Average: " + String.format("%.5f", arg.getAvgLatency()) + " secs\n" +
                "\n" +
                " Requests/sec: " + String.format("%.5f", 1.0 / arg.getAvgLatency()));

        System.out.println("\nLatency distribution:\n" +
                "\n" +
                " 10% in " + String.format("%.5f", arg.getPercentile10()) + " secs\n" +
                "\n" +
                " 25% in " + String.format("%.5f", arg.getPercentile25()) + " secs\n" +
                "\n" +
                " 50% in " + String.format("%.5f", arg.getPercentile50()) + " secs\n" +
                "\n" +
                " 75% in " + String.format("%.5f", arg.getPercentile75()) + " secs\n" +
                "\n" +
                " 90% in " + String.format("%.5f", arg.getPercentile90()) + " secs\n" +
                "\n" +
                " 95% in " + String.format("%.5f", arg.getPercentile95()) + " secs\n" +
                "\n" +
                " 99% in " + String.format("%.5f", arg.getPercentile99()) + " secs");

        System.out.println("\nStatus code distribution:\n");

        arg.getCodes().forEach((k, v) -> {
            System.out.println(" [" + k + "] " + v + " responses\n");
        });
    }
}
