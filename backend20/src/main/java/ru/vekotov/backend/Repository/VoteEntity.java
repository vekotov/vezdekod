package ru.vekotov.backend.Repository;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "votes", indexes = {
        @Index(columnList = "artist")
})
public class VoteEntity {
    @Id
    @GeneratedValue
    private long id;

    private String artist;

    private Long timestamp;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
