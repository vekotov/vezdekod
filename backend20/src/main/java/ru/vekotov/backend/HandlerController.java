package ru.vekotov.backend;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.VerboseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vekotov.backend.DTO.AddVoteParam;
import ru.vekotov.backend.DTO.GetVotesResult;
import ru.vekotov.backend.Exceptions.TooManyRequestsException;

import javax.validation.Valid;

@RestController
public class HandlerController {
    @Autowired
    DataService ds;

    @Autowired
    BucketService bs;

    @PostMapping("/votes")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<String> addVoteHandler(@RequestBody @Valid AddVoteParam addVoteParam) {
        Bucket bucket = bs.resolveBucket(addVoteParam.getPhone());
        VerboseResult<ConsumptionProbe> result = bucket.asVerbose().tryConsumeAndReturnRemaining(1);
        if (!result.getValue().isConsumed()) {
            throw new TooManyRequestsException(result);
        }

        ds.addVote(addVoteParam.getArtist());
        return ResponseEntity.status(HttpStatus.CREATED).headers(getXRateHeaders(result)).body("");
    }

    @GetMapping("/votes")
    @ResponseBody
    public GetVotesResult getVotesHandler() {
        return ds.getVotes();
    }

    @ExceptionHandler({ TooManyRequestsException.class })
    public ResponseEntity<String> handleException(TooManyRequestsException e) {
        HttpHeaders responseHeaders = getXRateHeaders(e.getBucket());

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders)
                .body("");
    }

    private HttpHeaders getXRateHeaders(VerboseResult<ConsumptionProbe> b) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-ratelimit-limit", "" + bs.getRequestLimit());
        responseHeaders.set("x-ratelimit-remaining", "" + b.getDiagnostics().getAvailableTokens());
        responseHeaders.set("x-ratelimit-reset", "" + (b.getValue().getNanosToWaitForReset() / 1_000_000_000L + System.currentTimeMillis() / 1000L));
        return responseHeaders;
    }
}
