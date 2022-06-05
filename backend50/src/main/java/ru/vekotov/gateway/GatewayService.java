package ru.vekotov.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vekotov.gateway.DTO.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class GatewayService {
    List<String> servers;
    private RestTemplate restTemplate;

    public GatewayService() {
        restTemplate = new RestTemplateBuilder().build();

        servers = Arrays.asList(System.getenv("servers").split(","));

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info("Server list is: ");
        for (String server : servers) {
            logger.info(server);
        }

        if (servers == null || servers.size() == 0) {
            throw new RuntimeException("Servers envvar is empty!");
        }
    }

    public ResponseEntity<String> addVote(AddVoteParam args) {
        String phone = args.getPhone();

        int code = Hashcode.crc16(phone.getBytes());
        String server = servers.get(code % servers.size());

        ResponseEntity<String> response = restTemplate.postForEntity("http://" + server + "/votes", args, String.class);
        return response;
    }

    public GetVotesResult getVotesResult() throws ExecutionException, InterruptedException {
        Map<String, Long> votes = new HashMap<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<GetVotesResult>> futures = new ArrayList<>();

        for (String server : servers) {
            Future<GetVotesResult> future = executor.submit(
                    () -> restTemplate.getForEntity("http://" + server + "/votes", GetVotesResult.class).getBody()
            );

            futures.add(future);
        }

        for (Future<GetVotesResult> future : futures) {
            GetVotesResult result = future.get();
            List<VotesResult> resultList = result.getData();
            for (VotesResult vote : resultList) {
                votes.compute(vote.getArtist(), (k, v) -> {
                    if (v == null) return vote.getVotes();
                    return v + vote.getVotes();
                });
            }
        }

        executor.shutdown();

        List<VotesResult> result = new ArrayList<>();

        for (Map.Entry<String, Long> entry : votes.entrySet()) {
            VotesResult vote = new VotesResult();
            vote.setVotes(entry.getValue());
            vote.setArtist(entry.getKey());
            result.add(vote);
        }

        GetVotesResult returnValue = new GetVotesResult();
        returnValue.setData(result);
        return returnValue;
    }

    public List<VotingStats> getVotingStatsResult(Long from, Long to, Long intervals, String artists)
            throws ExecutionException, InterruptedException {

        if (from == null) from = getSmallestTimestamp();
        if (to == null) to = getBiggestTimestamp();
        if (intervals == null) intervals = 10L;

        if (from == null || to == null) return new ArrayList<>();

        Map<Interval, Long> intervalVotes = new HashMap<>();

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<GetVotingStatsResult>> futures = new ArrayList<>();

        String args = "";
        args += "to=" + to + "&";
        args += "from=" + from + "&";
        args += "intervals=" + intervals;
        if (artists != null) args += "&artists=" + artists;

        for (String server : servers) {
            String finalArgs = args;
            Future<GetVotingStatsResult> future = executor.submit(

                    () -> restTemplate.getForEntity(
                            "http://" + server + "/votes/stats?" + finalArgs,
                            GetVotingStatsResult.class
                    ).getBody()
            );

            futures.add(future);
        }

        for (Future<GetVotingStatsResult> future : futures) {
            GetVotingStatsResult result = future.get();

            for (VotingStats stats : result.getData()) {
                intervalVotes.compute(new Interval(stats.getStart(), stats.getEnd()), (k, v) -> {
                   if (v == null) return stats.getVotes();
                   return v + stats.getVotes();
                });
            }
        }

        List<VotingStats> stats = new ArrayList<>();

        for (Map.Entry<Interval, Long> entry : intervalVotes.entrySet()) {
            VotingStats interval = new VotingStats();
            interval.setStart(entry.getKey().getStart());
            interval.setEnd(entry.getKey().getEnd());
            interval.setVotes(entry.getValue());
            stats.add(interval);
        }

        stats.sort(Comparator.comparingLong(VotingStats::getStart));

        executor.shutdown();
        return stats;
    }

    private Long getSmallestTimestamp() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Long>> futures = new ArrayList<>();

        for (String server : servers) {
            Future<Long> future = executor.submit(
                    () -> restTemplate.getForEntity("http://" + server + "/votes/smallestTimestamp", Long.class).getBody()
            );

            futures.add(future);
        }

        Long smallest = null;
        for (Future<Long> future : futures) {
            Long result = future.get();
            if (smallest == null) smallest = result;
            else if (result != null) smallest = Math.min(smallest, result);
        }

        executor.shutdown();
        return smallest;
    }

    private Long getBiggestTimestamp() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Long>> futures = new ArrayList<>();

        for (String server : servers) {
            Future<Long> future = executor.submit(
                    () -> restTemplate.getForEntity("http://" + server + "/votes/biggestTimestamp", Long.class).getBody()
            );

            futures.add(future);
        }

        Long biggest = null;
        for (Future<Long> future : futures) {
            Long result = future.get();
            if (biggest == null) biggest = result;
            else if (result != null) biggest = Math.max(biggest, result);
        }

        executor.shutdown();
        return biggest;
    }
}
