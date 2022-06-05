import lombok.Getter;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleArgsReader {
    @Getter private String URL;
    @Getter private Long clientNum;
    @Getter private Long requestsNum;
    @Getter private List<String> artists = new ArrayList<>();

    public ConsoleArgsReader(String[] args) {
        Options options = new Options();

        Option number = new Option("n", "requests-number", true, "number of requests");
        number.setRequired(true);
        number.setType(Long.class);
        options.addOption(number);

        Option clients = new Option("c", "clients", true, "number of clients making requests");
        clients.setRequired(true);
        clients.setType(Long.class);
        options.addOption(clients);

        Option artists = new Option("a", "artists", true, "list of artists");
        artists.setRequired(true);
        options.addOption(artists);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(
                    "Usage: java -jar application.jar -n 100 -c 10 -a \"foo,bar\" localhost:8080",
                    options);

            System.exit(1);
        }

        this.requestsNum = Long.parseLong(cmd.getOptionValue("requests-number"));
        this.clientNum = Long.parseLong(cmd.getOptionValue("clients"));

        this.artists.addAll(Arrays.asList(cmd.getOptionValue("artists").split(",")));

        if (this.artists.size() == 0) {
            formatter.printHelp(
                    "Usage: java -jar application.jar -n 100 -c 10 -a \"foo,bar\" localhost:8080",
                    options);
            System.exit(1);
        }

        List<String> argList = cmd.getArgList();
        if (argList.size() == 0) {
            formatter.printHelp(
                    "Usage: java -jar application.jar -n 100 -c 10 -a \"foo,bar\" localhost:8080",
                    options);
            System.exit(1);
        }
        this.URL = argList.get(argList.size() - 1);
    }
}
