package com.puente4podcast.puente4podcast.dtos;

import com.puente4podcast.puente4podcast.models.Podcast;
import com.puente4podcast.puente4podcast.models.PodcastUser;

import java.util.List;
import java.util.stream.Collectors;

public class PodcastUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String mail;
    private String nacionality;
    private Podcast podcastUser;
    private boolean isAdmin;
    private List<ComentaryEpDTO> comentarySet;
    private List<FavoriteDTO> favoriteSet;
    private List<FavoriteArDTO> favoriteArSet;

    public PodcastUserDTO() {
    }

    public PodcastUserDTO(PodcastUser podcastUser) {
        this.id = podcastUser.getId();
        this.firstName = podcastUser.getFirstName();
        this.lastName = podcastUser.getLastName();
        this.userName = podcastUser.getUserName();
        this.mail = podcastUser.getMail();
        this.nacionality = podcastUser.getNacionality();
        this.podcastUser = podcastUser.getPodcastOw();
        this.isAdmin = podcastUser.isAdmin();
        this.comentarySet = podcastUser.getComentarySet().stream().map(comentary -> new ComentaryEpDTO(comentary)).collect(Collectors.toList());
        this.favoriteSet = podcastUser.getFavoriteSet().stream().map(favorite -> new FavoriteDTO(favorite)).collect(Collectors.toList());
        this.favoriteArSet = podcastUser.getFavoriteArSet().stream().map(ar -> new FavoriteArDTO(ar)).collect(Collectors.toList());

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMail() {
        return mail;
    }

    public String getNacionality() {
        return nacionality;
    }

//    public String getPassword() {
//        return password;
//    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Podcast getPodcastUser() {
        return podcastUser;
    }

    public List<ComentaryEpDTO> getComentarySet() {
        return comentarySet;
    }

    public List<FavoriteDTO> getFavoriteSet() {
        return favoriteSet;
    }

    public List<FavoriteArDTO> getFavoriteArSet() {
        return favoriteArSet;
    }
}