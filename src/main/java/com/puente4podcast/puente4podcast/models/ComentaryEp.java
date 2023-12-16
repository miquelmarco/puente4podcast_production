package com.puente4podcast.puente4podcast.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ComentaryEp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String text;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PodcastUser podcastUserCom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    public ComentaryEp() {
    }

    public ComentaryEp(String text, LocalDate date) {
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

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public PodcastUser getPodcastUserCom() {
        return podcastUserCom;
    }

    public void setPodcastUser(PodcastUser podcastUser) {
        this.podcastUserCom = podcastUser;
    }
}