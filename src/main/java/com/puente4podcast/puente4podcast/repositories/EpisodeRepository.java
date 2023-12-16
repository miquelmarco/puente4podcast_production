package com.puente4podcast.puente4podcast.repositories;

import com.puente4podcast.puente4podcast.models.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    Episode findByFeatured(boolean featured);
}
