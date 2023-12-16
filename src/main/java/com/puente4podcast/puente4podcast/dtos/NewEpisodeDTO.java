package com.puente4podcast.puente4podcast.dtos;

public class NewEpisodeDTO {
    private Byte epSeason;
    private Byte epEpisode;
    private String epName;
    private String epImg;
    private String epLinkYt;
    private String epLinkIVoox;
    private String epDuration;
    private String epDescription;
    private boolean epFeatured;

    public NewEpisodeDTO() {
    }

    public Byte getSeasonNumber() {
        return epSeason;
    }

    public Byte getEpisode() {
        return epEpisode;
    }

    public String getName() {
        return epName;
    }

    public String getImg() {
        return epImg;
    }

    public String getLinkYt() {
        return epLinkYt;
    }

    public String getLinkIvoox() {
        return epLinkIVoox;
    }

    public String getDuration() {
        return epDuration;
    }

    public String getDescription() {
        return epDescription;
    }

    public boolean isFeatured() {
        return epFeatured;
    }
}