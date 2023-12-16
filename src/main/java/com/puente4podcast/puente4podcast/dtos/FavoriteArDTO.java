package com.puente4podcast.puente4podcast.dtos;

import com.puente4podcast.puente4podcast.models.Archive;
import com.puente4podcast.puente4podcast.models.FavoriteAr;
import com.puente4podcast.puente4podcast.models.PodcastUser;

public class FavoriteArDTO {
    private Long id;
    private PodcastUser podcastUserArFav;
    private Archive archiveFav;

    public FavoriteArDTO() {
    }

    public FavoriteArDTO(FavoriteAr favoriteAr) {
        this.id = favoriteAr.getId();
        this.podcastUserArFav = favoriteAr.getPodcastUserArFav();
        this.archiveFav = favoriteAr.getArchiveFav();
    }

    public Long getId() {
        return id;
    }

    public PodcastUser getPodcastUserArFav() {
        return podcastUserArFav;
    }

    public Archive getArchiveFav() {
        return archiveFav;
    }
}
