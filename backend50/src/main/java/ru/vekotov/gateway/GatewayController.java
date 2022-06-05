package ru.vekotov.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vekotov.gateway.DTO.AddVoteParam;
import ru.vekotov.gateway.DTO.GetVotesResult;
import ru.vekotov.gateway.DTO.GetVotingStatsResult;

import java.util.concurrent.ExecutionException;

@Controller
public class GatewayController {
    @Autowired
    GatewayService service;

    @PostMapping("/votes")
    @ResponseBody
    ResponseEntity<String> addVoteHandler(@RequestBody AddVoteParam arg) {
        return service.addVote(arg);
    }

    @GetMapping("/votes")
    @ResponseBody
    GetVotesResult getVotesHandler() throws ExecutionException, InterruptedException {
        return service.getVotesResult();
    }

    @GetMapping("/votes/stats")
    @ResponseBody
    GetVotingStatsResult getVotesHandler(
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to,
            @RequestParam(required = false) Long intervals,
            @RequestParam(required = false) String artists
    ) throws ExecutionException, InterruptedException {
        GetVotingStatsResult result = new GetVotingStatsResult();
        result.setData(service.getVotingStatsResult(from, to, intervals, artists));
        return result;
    }
}
