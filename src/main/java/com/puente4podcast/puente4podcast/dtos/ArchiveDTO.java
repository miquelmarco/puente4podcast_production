package com.puente4podcast.puente4podcast.dtos;

import com.puente4podcast.puente4podcast.models.Archive;

import java.util.List;
import java.util.stream.Collectors;

public class ArchiveDTO {
    private Long id;
    private Byte archiveNumber;
    private String name;
    private String img;
    private String linkYt;
    private String linkIvoox;
    private String duration;
    private String category;
    private String description;
    private boolean featured;
    private List<ComentaryArDTO> comentaryArDTOList;
    private List<FavoriteArDTO> favoriteArDTOList;

    public ArchiveDTO() {
    }

    public ArchiveDTO(Archive archive) {
        this.id = archive.getId();
        this.archiveNumber = archive.getArchiveNumber();
        this.name = archive.getName();
        this.img = archive.getImg();
        this.linkYt = archive.getLinkYt();
        this.linkIvoox = archive.getLinkIvoox();
        this.duration = archive.getDuration();
        this.category = archive.getCategory();
        this.description = archive.getDescription();
        this.featured = archive.isFeatured();
        this.comentaryArDTOList = archive.getComentaryArSet().stream().map(comentary -> new ComentaryArDTO(comentary)).collect(Collectors.toList());
        this.favoriteArDTOList = archive.getFavoriteArSet().stream().map(FavoriteArDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public Byte getArchiveNumber() {
        return archiveNumber;
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

    public String getDescription() {
        return description;
    }

    public boolean isFeatured() {
        return featured;
    }

    public List<ComentaryArDTO> getComentaryArDTOList() {
        return comentaryArDTOList;
    }

    public List<FavoriteArDTO> getFavoriteArDTOList() {
        return favoriteArDTOList;
    }
}