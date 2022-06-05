package ru.vekotov.gateway.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GetVotesResult {
    private List<VotesResult> data;
}
