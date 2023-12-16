package com.puente4podcast.puente4podcast.dtos;
import com.puente4podcast.puente4podcast.models.ComentaryEp;
import com.puente4podcast.puente4podcast.models.Episode;

import java.time.LocalDate;

public class ComentaryEpDTO {
    private Long id;
    private String text;
    private LocalDate date;
    private String podcastUserCom;
    private Episode episode;

    public ComentaryEpDTO() {
    }
    public ComentaryEpDTO(ComentaryEp comentary) {
        this.id = comentary.getId();
        this.text = comentary.getText();
        this.date = comentary.getDate();
        this.podcastUserCom = comentary.getPodcastUserCom().getUserName();
        this.episode = comentary.getEpisode();
    }
    public Long getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getPodcastUserCom() {
        return podcastUserCom;
    }
    public Episode getEpisode() {
        return episode;
    }
}