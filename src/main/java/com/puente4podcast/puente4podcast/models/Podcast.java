package com.puente4podcast.puente4podcast.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private String description;
    private String banner;
    @OneToMany(mappedBy = "podcastUser", fetch = FetchType.EAGER)
    private Set<PodcastUser> podcastUsers = new HashSet<>();
    @OneToMany(mappedBy = "podcastsSeason", fetch = FetchType.EAGER)
    private Set<Season> seasons = new HashSet<>();
    @OneToMany(mappedBy = "podcastArchives", fetch = FetchType.EAGER)
    private Set<Archive> archives = new HashSet<>();
    public Podcast() {
    }
    public Podcast(String name, String description, String banner) {
        this.name = name;
        this.description = description;
        this.banner = banner;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }
    public Set<Season> getSeason() {
        return seasons;
    }
    public void setSeason(Set<Season> season) {
        this.seasons = season;
    }
    public Set<Archive> getArchive() {
        return archives;
    }
    public void setArchive(Set<Archive> archive) {
        this.archives = archive;
    }
    @JsonIgnore
    public Set<PodcastUser> getPodcastUsers() {
        return podcastUsers;
    }
    public void setPodcastUsers(Set<PodcastUser> podcastUsers) {
        this.podcastUsers = podcastUsers;
    }
    @JsonIgnore
    public Set<Season> getSeasons() {
        return seasons;
    }
    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }
    @JsonIgnore
    public Set<Archive> getArchives() {
        return archives;
    }
    public void setArchives(Set<Archive> archives) {
        this.archives = archives;
    }
    public void addSeason(Season season) {
        season.setPodcast(this);
        seasons.add(season);
    }
    public void addAllSeasons(List<Season> seasonList) {
        for (Season season : seasonList) {
            this.addSeason(season);
        }
    }
    public void addArchive(Archive archive) {
        archive.setPodcast(this);
        archives.add(archive);
    }
    public void addAllArchives(List<Archive> archiveList) {
        for (Archive archive : archiveList) {
            this.addArchive(archive);
        }
    }
    public void addPodcastUser(PodcastUser podcastUser) {
        podcastUser.setPodcastOw(this);
        podcastUsers.add(podcastUser);
    }
    public void addAllPodcastUsers(List<PodcastUser> podcastUserList) {
        for (PodcastUser podcastUser : podcastUserList) {
            this.addPodcastUser(podcastUser);
        }
    }
}