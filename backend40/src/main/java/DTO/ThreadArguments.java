package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ThreadArguments {
    private List<String> artistList;
    private Long numberOfRequests;
    private String URL;
}
