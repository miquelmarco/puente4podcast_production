package com.puente4podcast.puente4podcast.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class FavoriteAr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PodcastUser podcastUserArFav;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "archive_id")
    private Archive archiveFav;

    public FavoriteAr() {
    }

    public FavoriteAr(PodcastUser podcastUserArFav, Archive archiveFav) {
        this.podcastUserArFav = podcastUserArFav;
        this.archiveFav = archiveFav;
    }

    public Long getId() {
        return id;
    }

    public PodcastUser getPodcastUserArFav() {
        return podcastUserArFav;
    }

    public void setPodcastUserArFav(PodcastUser podcastUserArFav) {
        this.podcastUserArFav = podcastUserArFav;
    }

    public Archive getArchiveFav() {
        return archiveFav;
    }

    public void setArchiveFav(Archive archiveFav) {
        this.archiveFav = archiveFav;
    }
}