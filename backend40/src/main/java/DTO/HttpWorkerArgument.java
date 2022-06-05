package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpWorkerArgument {
    private String URL;
    private String body;
}
