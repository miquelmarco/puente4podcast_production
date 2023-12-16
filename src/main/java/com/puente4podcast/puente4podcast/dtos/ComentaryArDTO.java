package com.puente4podcast.puente4podcast.dtos;

import com.puente4podcast.puente4podcast.models.Archive;
import com.puente4podcast.puente4podcast.models.ComentaryAr;

import java.time.LocalDate;

public class ComentaryArDTO {
    private Long id;
    private String text;
    private LocalDate date;
    private String podcastUserArCom;
    private Archive archive;

    public ComentaryArDTO() {
    }

    public ComentaryArDTO(ComentaryAr comentaryAr) {
        this.id = comentaryAr.getId();
        this.text = comentaryAr.getText();
        this.date = comentaryAr.getDate();
        this.podcastUserArCom = comentaryAr.getPodcastUserArCom().getUserName();
        this.archive = comentaryAr.getArchive();
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

    public String getPodcastUserArCom() {
        return podcastUserArCom;
    }

    public Archive getArchive() {
        return archive;
    }
}