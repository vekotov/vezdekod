import DTO.HttpWorkerArgument;
import DTO.HttpWorkerResponse;
import DTO.ThreadArguments;
import DTO.ThreadResponse;

import java.util.*;
import java.util.concurrent.*;

public class ThreadWorker {
    private Future<ThreadResponse> future;
    private ExecutorService executor;

    public ThreadWorker(ThreadArguments args) {
        executor = Executors.newSingleThreadExecutor();
        Callable<ThreadResponse> callable = () -> work(args);
        future = executor.submit(callable);
    }

    public ThreadResponse joinThread() {
        try {
            ThreadResponse response = future.get();
            executor.shutdown();
            return response;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.exit(1);
        return null;
    }

    public static ThreadResponse work(ThreadArguments args) {
        List<Double> latencies = new ArrayList<>();
        Map<Integer, Long> codes = new HashMap<>();

        HttpWorker worker = new HttpWorker();

        Random random = new Random();
        random.setSeed(System.nanoTime());

        for (int i = 0; i < args.getNumberOfRequests(); i++) {
            String phoneNumber = "" + (9_000_000_000L + random.nextInt(1_000_000_000));
            String artist = args.getArtistList().get(random.nextInt(args.getArtistList().size()));

            String body = "{\"phone\":\"" + phoneNumber + "\",\"artist\":\"" + artist + "\"}";
            String URL = "http://" + args.getURL() + "/votes";

            HttpWorkerResponse response = worker.sendRequest(new HttpWorkerArgument(URL, body));

            latencies.add(response.getLatency());
            codes.compute(response.getCode(), (k, v) -> {
                if (v == null) return 1L;
                return v + 1L;
            });
        }

        return new ThreadResponse(latencies, codes);
    }
}
