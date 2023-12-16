package com.puente4podcast.puente4podcast.repositories;

import com.puente4podcast.puente4podcast.models.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    boolean existsByArchiveNumber(Byte number);
}
