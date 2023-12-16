package com.puente4podcast.puente4podcast.repositories;

import com.puente4podcast.puente4podcast.models.PodcastUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PodcastUserRepository extends JpaRepository<PodcastUser, Long> {
    PodcastUser findByMail(String mail);
    PodcastUser findByUserName(String userName);
}