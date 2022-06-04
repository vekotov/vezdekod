package ru.vekotov.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vekotov.backend.DTO.AddVoteParam;
import ru.vekotov.backend.DTO.GetVotesResult;

import javax.validation.Valid;

@RestController
public class HandlerController {
    @Autowired
    DataService ds;

    @PostMapping("/votes")
    @ResponseStatus(HttpStatus.CREATED)
    void addVoteHandler(@RequestBody @Valid AddVoteParam addVoteParam) {
        ds.addVote(addVoteParam.getArtist());
    }

    @GetMapping("/votes")
    @ResponseBody
    GetVotesResult getVotesHandler() {
        return ds.getVotes();
    }
}
