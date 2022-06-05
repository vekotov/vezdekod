package ru.vekotov.backend.Exceptions;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.VerboseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@ResponseStatus(value= HttpStatus.TOO_MANY_REQUESTS, reason="Too much requests")
public class TooManyRequestsException extends RuntimeException {
    @Getter @Setter
    private VerboseResult<ConsumptionProbe> bucket;
}
