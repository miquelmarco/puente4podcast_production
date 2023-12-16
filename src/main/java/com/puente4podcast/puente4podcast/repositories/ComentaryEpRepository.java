package com.puente4podcast.puente4podcast.repositories;

import com.puente4podcast.puente4podcast.models.ComentaryEp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ComentaryEpRepository extends JpaRepository<ComentaryEp, Long> {
    Set<ComentaryEp> findByEpisode(Long id);
}