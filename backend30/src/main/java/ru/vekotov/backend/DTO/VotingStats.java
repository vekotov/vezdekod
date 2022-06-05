package ru.vekotov.backend.DTO;

import lombok.Data;

@Data
public class VotingStats {
    private Long start;
    private Long end;
    private Long votes;
}
