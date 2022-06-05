package ru.vekotov.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vekotov.backend.DTO.GetVotesResult;
import ru.vekotov.backend.DTO.VotesResult;
import ru.vekotov.backend.DTO.VotingStats;
import ru.vekotov.backend.Exceptions.ArtistNotFoundException;
import ru.vekotov.backend.Repository.VoteEntity;
import ru.vekotov.backend.Repository.VoteRepository;

import java.util.*;

@Service
public class DataService {
    @Autowired
    private VoteRepository repository;
    private Set<String> artistsSet = new HashSet<>();

    public DataService() {
        String artists = System.getenv("artists");

        if (artists == null || artists.equals("")) {
            throw new RuntimeException("Artists envvar is empty or not defined");
        }

        artistsSet.addAll(Arrays.asList(artists.split(",")));
    }

    public boolean addVote(String artist) {
        if (!artistsSet.contains(artist))
            throw new ArtistNotFoundException();

        VoteEntity entity = new VoteEntity();
        entity.setArtist(artist);
        entity.setTimestamp(System.currentTimeMillis() / 1000L);
        repository.save(entity);

        return true;
    }

    public GetVotesResult getVotes() {
        List<VotesResult> results = new LinkedList<>();
        artistsSet.forEach(artist -> {
            VotesResult result = new VotesResult();
            result.setArtist(artist);
            result.setVotes(repository.countByArtist(artist));
            results.add(result);
        });
        GetVotesResult getVotesResult = new GetVotesResult();
        getVotesResult.setData(results);

        return getVotesResult;
    }

    public List<VotingStats> GetVotingStats(Long from, Long to, Long intervals, String artists) {
        if (from == null) {
            from = repository.smallestTimestamp();
        }
        if (to == null) {
            to = repository.biggestTimestamp();
        }

        if (from == null || to == null) {
            return new LinkedList<>();
        }

        if (intervals == null) {
            intervals = 10L;
        }

        if (intervals < 1 || to < from) return new LinkedList<>();

        List<String> artistList = artists != null ? Arrays.asList(artists.split(",")) : new ArrayList<>();

        if (intervals > (to - from)) intervals = to - from + 1;

        List<VotingStats> result = new LinkedList<>();

        Long eachInterval = (to - from + 1) / intervals;
        Long leftovers = (to - from + 1) % intervals;

        Long currentFrom = from;

        for (long i = 0L; i < intervals; i++) {
            Long currentTo = currentFrom + eachInterval - 1;

            if (i >= intervals - leftovers) {
                currentTo++;
            }

            Long count;
            if (artistList.size() == 0) {
                count = repository.countByTime(currentFrom, currentTo);
            } else {
                count = repository.countByTimeAndArtists(currentFrom, currentTo, artistList);
            }

            VotingStats votingStats = new VotingStats();
            votingStats.setStart(currentFrom);
            votingStats.setEnd(currentTo);
            votingStats.setVotes(count);

            result.add(votingStats);

            currentFrom = currentTo + 1;
        }

        return result;
    }

    public Long getSmallestTimestamp() {
        return repository.smallestTimestamp();
    }

    public Long getBiggestTimestamp() {
        return repository.biggestTimestamp();
    }
}
