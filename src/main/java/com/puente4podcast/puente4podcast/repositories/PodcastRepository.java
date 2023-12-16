package com.puente4podcast.puente4podcast.repositories;

import com.puente4podcast.puente4podcast.models.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PodcastRepository extends JpaRepository<Podcast, Long> {
    Podcast findByName(String puente4podcast);
}