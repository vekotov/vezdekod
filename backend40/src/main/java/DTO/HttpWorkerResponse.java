package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpWorkerResponse {
    private Double latency;
    private Integer code;
}
