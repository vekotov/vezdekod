package ru.vekotov.backend.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends CrudRepository<VoteEntity, Long> {
    long countByArtist(String artist);

    @Query("select COUNT(id) from votes where timestamp >= :fromTime AND timestamp <= :toTime")
    Long countByTime(@Param("fromTime") Long from, @Param("toTime") Long to);

    @Query("select COUNT(id) " +
            "from votes " +
            "where timestamp >= :fromTime " +
            "AND timestamp <= :toTime " +
            "AND artist IN (:filterArtists)")
    Long countByTimeAndArtists(
            @Param("fromTime") Long from,
            @Param("toTime") Long to,
            @Param("filterArtists") List<String> artists
    );

    @Query("select MIN(timestamp) from votes")
    Long smallestTimestamp();

    @Query("select MAX(timestamp) from votes")
    Long biggestTimestamp();
}
