package com.puente4podcast.puente4podcast.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ComentaryAr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String text;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PodcastUser podcastUserArCom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "archive_id")
    private Archive archive;

    public ComentaryAr() {
    }

    public ComentaryAr(String text, LocalDate date) {
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PodcastUser getPodcastUserArCom() {
        return podcastUserArCom;
    }

    public void setPodcastUserArCom(PodcastUser podcastUserArCom) {
        this.podcastUserArCom = podcastUserArCom;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}