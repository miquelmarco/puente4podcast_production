package com.puente4podcast.puente4podcast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Byte seasonNumber;
    private Byte episode;
    private String name;
    private String img;
    private String linkYt;
    private String linkIvoox;
    private String duration;
    private String category;
    @Column(length = 1000)
    private String description;
    private boolean featured;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id")
    private Season epSeason;
    @OneToMany(mappedBy = "episode", fetch = FetchType.EAGER)
    private Set<ComentaryEp> comentarySet = new HashSet<>();
    @OneToMany(mappedBy = "episodeFav", fetch = FetchType.EAGER)
    private Set<Favorite> favoriteSet = new HashSet<>();

    public Episode() {
    }

    public Episode(Byte seasonNumber, Byte episode, String name, String img, String linkYt, String linkIvoox, String duration, String category, String description, boolean featured) {
        this.seasonNumber = seasonNumber;
        this.episode = episode;
        this.name = name;
        this.img = img;
        this.linkYt = linkYt;
        this.linkIvoox = linkIvoox;
        this.duration = duration;
        this.category = category;
        this.description = description;
        this.featured = featured;
    }

    public Long getId() {
        return id;
    }

    public Byte getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Byte seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Byte getEpisode() {
        return episode;
    }

    public void setEpisode(Byte episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLinkYt() {
        return linkYt;
    }

    public void setLinkYt(String linkYt) {
        this.linkYt = linkYt;
    }

    public String getLinkIvoox() {
        return linkIvoox;
    }

    public void setLinkIvoox(String linkIvoox) {
        this.linkIvoox = linkIvoox;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    @JsonIgnore
    public Season getEpSeason() {
        return epSeason;
    }

    public void setEpSeason(Season epSeason) {
        this.epSeason = epSeason;
    }

    @JsonIgnore
    public Season getSeason() {
        return epSeason;
    }

    public void setSeason(Season season) {
        this.epSeason = season;
    }

    @JsonIgnore
    public Set<ComentaryEp> getComentarySet() {
        return comentarySet;
    }

    public void setComentarySet(Set<ComentaryEp> comentarySet) {
        this.comentarySet = comentarySet;
    }

    @JsonIgnore
    public Set<Favorite> getFavoriteSet() {
        return favoriteSet;
    }

    public void setFavoriteSet(Set<Favorite> favoriteSet) {
        this.favoriteSet = favoriteSet;
    }

    public void addComentary(ComentaryEp comentary) {
        comentary.setEpisode(this);
        comentarySet.add(comentary);
    }

    public void addAllComentary(List<ComentaryEp> comentaryList) {
        for (ComentaryEp comentary : comentaryList) {
            this.addComentary(comentary);
        }
    }

    public void addFavorite(Favorite favorite) {
        favorite.setEpisodeFav(this);
        favoriteSet.add(favorite);
    }

    public void addAllFavorites(List<Favorite> favoriteList) {
        for (Favorite favorite : favoriteList) {
            this.addFavorite(favorite);
        }
    }

    public void removeFavorite(Favorite favorite) {
        favoriteSet.remove(favorite);
        favorite.setEpisodeFav(null);
    }
}