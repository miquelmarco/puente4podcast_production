package com.puente4podcast.puente4podcast.repositories;

import com.puente4podcast.puente4podcast.models.ComentaryAr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ComentaryArRepository extends JpaRepository<ComentaryAr, Long> {
    Set<ComentaryAr> findByArchive(Long id);
}