package com.puente4podcast.puente4podcast.dtos;
import com.puente4podcast.puente4podcast.models.Episode;
import com.puente4podcast.puente4podcast.models.Favorite;
import com.puente4podcast.puente4podcast.models.PodcastUser;
public class FavoriteDTO {
    private Long id;
    private PodcastUser podcastUserFav;
    private Episode episodeFav;
    public FavoriteDTO() {
    }
    public FavoriteDTO(Favorite favorite) {
        this.id = favorite.getId();
        this.podcastUserFav = favorite.getUserFav();
        this.episodeFav = favorite.getEpisodeFav();
    }
    public Long getId() {
        return id;
    }
    public PodcastUser getPodcastUserFav() {
        return podcastUserFav;
    }
    public Episode getEpisodeFav() {
        return episodeFav;
    }
}