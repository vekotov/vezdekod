import DTO.CalculatorArguments;
import DTO.CalculatorResponse;
import DTO.ThreadArguments;
import DTO.ThreadResponse;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleArgsReader consoleArgsReader = new ConsoleArgsReader(args);

        List<ThreadWorker> workerList = new ArrayList<>();

        Long forEachClient = consoleArgsReader.getRequestsNum() / consoleArgsReader.getClientNum();
        Long leftovers = consoleArgsReader.getRequestsNum() % consoleArgsReader.getClientNum();
        for (int i = 0; i < consoleArgsReader.getClientNum(); i++) {
            Long requestNum = forEachClient;
            if (i >= consoleArgsReader.getClientNum() - leftovers) {
                requestNum++;
            }

            if (requestNum == 0) continue;

            workerList.add(new ThreadWorker(new ThreadArguments(
                    consoleArgsReader.getArtists(),
                    requestNum,
                    consoleArgsReader.getURL()
            )));
        }

        List<ThreadResponse> responses = new ArrayList<>();
        for (ThreadWorker worker : workerList) {
            responses.add(worker.joinThread());
        }

        Calculator calc = new Calculator();
        CalculatorResponse calculatorResponse = calc.calculate(new CalculatorArguments(responses));

        ResultPrinter printer = new ResultPrinter();
        printer.printResults(calculatorResponse);
    }
}
