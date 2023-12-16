package com.puente4podcast.puente4podcast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PodcastUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String nacionality;
    private String password;
    private boolean isAdmin;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "podcastOw_id")
    private Podcast podcastUser;
    @OneToMany(mappedBy = "podcastUserCom", fetch = FetchType.EAGER)
    private Set<ComentaryEp> comentarySet = new HashSet<>();
    @OneToMany(mappedBy = "podcastUserFav")
    private Set<Favorite> favoriteSet = new HashSet<>();
    @OneToMany(mappedBy = "podcastUserArFav")
    private Set<FavoriteAr> favoriteArSet = new HashSet<>();
    @OneToMany(mappedBy = "podcastUserArCom")
    private Set<ComentaryAr> comentaryArSet = new HashSet<>();


    public PodcastUser() {
    }

    public PodcastUser(String firstName, String lastName, String userName, String mail, String nacionality, String password, boolean isAdmin, Podcast podcastUser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mail = mail;
        this.nacionality = nacionality;
        this.password = password;
        this.isAdmin = isAdmin;
        this.podcastUser = podcastUser;
    }

    public PodcastUser(String firstName, String lastName, String userName, String mail, String nacionality, String password, Set<ComentaryEp> comentarySet, Set<Favorite> favoriteSet, Set<FavoriteAr> favoriteArSet, Set<ComentaryAr> comentaryArSet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mail = mail;
        this.nacionality = nacionality;
        this.password = password;
        this.comentarySet = comentarySet;
        this.favoriteSet = favoriteSet;
        this.favoriteArSet = favoriteArSet;
        this.comentaryArSet = comentaryArSet;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Podcast getPodcastUser() {
        return podcastUser;
    }

    public void setPodcastUser(Podcast podcastUser) {
        this.podcastUser = podcastUser;
    }

    @JsonIgnore
    public Set<ComentaryEp> getComentarySet() {
        return comentarySet;
    }

    public void setComentarySet(Set<ComentaryEp> comentarySet) {
        this.comentarySet = comentarySet;
    }

    @JsonIgnore
    public Set<ComentaryAr> getComentaryArSet() {
        return comentaryArSet;
    }

    public void setComentaryArSet(Set<ComentaryAr> comentaryArSet) {
        this.comentaryArSet = comentaryArSet;
    }

    @JsonIgnore
    public Set<Favorite> getFavoriteSet() {
        return favoriteSet;
    }

    public void setFavoriteSet(Set<Favorite> favoriteSet) {
        this.favoriteSet = favoriteSet;
    }

    @JsonIgnore
    public Set<FavoriteAr> getFavoriteArSet() {
        return favoriteArSet;
    }

    public void setFavoriteArSet(Set<FavoriteAr> favoriteArSet) {
        this.favoriteArSet = favoriteArSet;
    }

    @JsonIgnore
    public Podcast getPodcastOw() {
        return podcastUser;
    }

    public void setPodcastOw(Podcast podcastOw) {
        this.podcastUser = podcastOw;
    }

    public void addComentary(ComentaryEp comentary) {
        comentary.setPodcastUser(this);
        comentarySet.add(comentary);
    }

    public void addAllComentary(List<ComentaryEp> comentaryList) {
        for (ComentaryEp comentary : comentaryList) {
            this.addComentary(comentary);
        }
    }

    public void addArComentary(ComentaryAr comentary) {
        comentary.setPodcastUserArCom(this);
        comentaryArSet.add(comentary);
    }

    public void addAllArComentary(List<ComentaryAr> comentaryList) {
        for (ComentaryAr comentary : comentaryList) {
            this.addArComentary(comentary);
        }
    }

    public void addFavorite(Favorite favorite) {
        favorite.setUserFav(this);
        favoriteSet.add(favorite);
    }

    public void addAllFavorite(List<Favorite> favoriteList) {
        for (Favorite favorite : favoriteList) {
            this.addFavorite(favorite);
        }
    }

    public void removeFavorite(Favorite favorite) {
        favoriteSet.remove(favorite);
        favorite.setUserFav(null);
    }

    public void addFavoriteAr(FavoriteAr favoriteAr) {
        favoriteAr.setPodcastUserArFav(this);
        favoriteArSet.add(favoriteAr);
    }

    public void addAllArFavorite(List<FavoriteAr> favoriteArList) {
        for (FavoriteAr favoriteAr : favoriteArList) {
            this.addFavoriteAr(favoriteAr);
        }
    }

    public void removeArFavorite(FavoriteAr favoriteAr) {
        favoriteArSet.remove(favoriteAr);
        favoriteAr.setPodcastUserArFav(null);
    }
}