package ru.vekotov.backend.Repository;

import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<VoteEntity, Long> {
    long countByArtist(String artist);
}
