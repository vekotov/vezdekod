package ru.vekotov.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vekotov.backend.DTO.GetVotesResult;
import ru.vekotov.backend.DTO.VotesResult;
import ru.vekotov.backend.Exceptions.ArtistNotFoundException;
import ru.vekotov.backend.Repository.VoteEntity;
import ru.vekotov.backend.Repository.VoteRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

@Service
public class DataService {
    @Autowired
    private VoteRepository repository;
    private HashSet<String> artistsSet = new HashSet<>();

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
        LinkedList<VotesResult> results = new LinkedList<>();
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
}
