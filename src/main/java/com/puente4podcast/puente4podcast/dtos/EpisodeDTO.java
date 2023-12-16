package com.puente4podcast.puente4podcast.dtos;

import com.puente4podcast.puente4podcast.models.Episode;
import com.puente4podcast.puente4podcast.models.Season;

import java.util.List;
import java.util.stream.Collectors;

public class EpisodeDTO {
    private Long id;
    private Byte seasonNumber;
    private Byte episode;
    private String name;
    private String img;
    private String linkYt;
    private String linkIvoox;
    private String duration;
    private String category;
    private Season epSeason;
    private String description;
    private boolean featured;
    private List<ComentaryEpDTO> comentarySet;
    private List<FavoriteDTO> favoriteSet;

    public EpisodeDTO() {
    }

    public EpisodeDTO(Episode episode) {
        this.id = episode.getId();
        this.seasonNumber = episode.getSeasonNumber();
        this.episode = episode.getEpisode();
        this.name = episode.getName();
        this.img = episode.getImg();
        this.linkYt = episode.getLinkYt();
        this.linkIvoox = episode.getLinkIvoox();
        this.duration = episode.getDuration();
        this.category = episode.getCategory();
        this.epSeason = episode.getSeason();
        this.featured = episode.isFeatured();
        this.description = episode.getDescription();
        this.comentarySet = episode.getComentarySet().stream().map(comentary -> new ComentaryEpDTO(comentary)).collect(Collectors.toList());
        this.favoriteSet = episode.getFavoriteSet().stream().map(FavoriteDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public Byte getSeasonNumber() {
        return seasonNumber;
    }

    public Byte getEpisode() {
        return episode;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getLinkYt() {
        return linkYt;
    }

    public String getLinkIvoox() {
        return linkIvoox;
    }

    public String getDuration() {
        return duration;
    }

    public String getCategory() {
        return category;
    }

    public Season getEpSeason() {
        return epSeason;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFeatured() {
        return featured;
    }

    public List<ComentaryEpDTO> getComentarySet() {
        return comentarySet;
    }

    public List<FavoriteDTO> getFavoriteSet() {
        return favoriteSet;
    }
}