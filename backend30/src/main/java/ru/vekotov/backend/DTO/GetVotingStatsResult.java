package ru.vekotov.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GetVotingStatsResult {
    private List<VotingStats> data;
}
