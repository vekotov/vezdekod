package ru.vekotov.backend;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BucketService {
    @Getter
    private Long requestLimit;
    private Long limitReset;

    public BucketService() {
        String requestLimitStr = System.getenv("request-limit");
        String limitResetStr = System.getenv("limit-reset");

        if (requestLimitStr == null || requestLimitStr.equals("")) {
            throw new RuntimeException("request-limit envvar is not defined!");
        }

        if (limitResetStr == null || limitResetStr.equals("")) {
            throw new RuntimeException("limit-reset envvar is not defined!");
        }

        try {
            requestLimit = Long.parseLong(requestLimitStr);
            limitReset = Long.parseLong(limitResetStr);
        } catch (Exception e) {
            throw new RuntimeException("limit-reset or request-limit envvar is not a number!");
        }

        if (requestLimit <= 0 || limitReset <= 0) {
            throw new RuntimeException("limit-reset or request-limit must be greater than zero!");
        }
    }

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String phone) {
        return cache.computeIfAbsent(phone, this::newBucket);
    }

    private Bucket newBucket(String phone) {
        return Bucket
                .builder()
                .addLimit(
                        Bandwidth.classic(requestLimit, Refill.intervally(requestLimit, Duration.ofSeconds(limitReset)))
                ).build();
    }
}
