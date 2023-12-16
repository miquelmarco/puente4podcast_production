package com.puente4podcast.puente4podcast.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PodcastUser podcastUserFav;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "episode_id")
    private Episode episodeFav;
    public Favorite() {
    }
    public Favorite(PodcastUser podcastUserFav, Episode episodeFav) {
        this.podcastUserFav = podcastUserFav;
        this.episodeFav = episodeFav;
    }
    public Long getId() {
        return id;
    }
    public PodcastUser getUserFav() {
        return podcastUserFav;
    }
    public void setUserFav(PodcastUser podcastUserFav) {
        this.podcastUserFav = podcastUserFav;
    }
    public Episode getEpisodeFav() {
        return episodeFav;
    }
    public void setEpisodeFav(Episode episodeFav) {
        this.episodeFav = episodeFav;
    }
}