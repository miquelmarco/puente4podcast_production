package com.puente4podcast.puente4podcast.controllers;

import com.puente4podcast.puente4podcast.dtos.ArchiveDTO;
import com.puente4podcast.puente4podcast.dtos.EpisodeDTO;
import com.puente4podcast.puente4podcast.models.*;
import com.puente4podcast.puente4podcast.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FavoriteController {

    @Autowired
    private PodcastUserRepository podcastUserRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    FavoriteArRepository favoriteArRepository;

    @PostMapping("/favorite/addEpFav")
    public ResponseEntity<?> addFavEp(Authentication authentication, @RequestParam Long id) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        Episode episode = episodeRepository.findById(id).orElse(null);
        if (podcastUser != null && episode != null) {
            if (podcastUser.getFavoriteSet().stream().anyMatch(fav -> fav.getEpisodeFav().equals(episode))) {
                return new ResponseEntity<>("El episodio ya está en favoritos", HttpStatus.CONFLICT);
            }
            Favorite favorite = new Favorite(podcastUser, episode);
            podcastUser.addFavorite(favorite);
            episode.addFavorite(favorite);
            favoriteRepository.save(favorite);
            podcastUserRepository.save(podcastUser);
            episodeRepository.save(episode);
            return new ResponseEntity<>("Agregado a Favoritos", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se ha podido agregar", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/favorite/addArFav")
    public ResponseEntity<?> addFavAr(Authentication authentication, @RequestParam Long id) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        Archive archive = archiveRepository.findById(id).orElse(null);
        if (podcastUser != null && archive != null) {
            if (podcastUser.getFavoriteArSet().stream().anyMatch(fav -> fav.getArchiveFav().equals(archive))) {
                return new ResponseEntity<>("El Archivo ya está en favoritos", HttpStatus.CONFLICT);
            }
            FavoriteAr favoriteAr = new FavoriteAr(podcastUser, archive);
            podcastUser.addFavoriteAr(favoriteAr);
            archive.addArFavorite(favoriteAr);
            favoriteArRepository.save(favoriteAr);
            podcastUserRepository.save(podcastUser);
            archiveRepository.save(archive);
            return new ResponseEntity<>("Agregado a Favoritos", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se ha podido agregar", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/favorite/removeEpFav")
    public ResponseEntity<?> removeFavEp(Authentication authentication, @RequestParam Long id) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        Episode episode = episodeRepository.findById(id).orElse(null);
        if (podcastUser != null && episode != null) {
            Favorite existingFavorite = podcastUser.getFavoriteSet().stream()
                    .filter(fav -> fav.getEpisodeFav().equals(episode))
                    .findFirst()
                    .orElse(null);
            if (existingFavorite != null) {
                podcastUser.removeFavorite(existingFavorite);
                episode.removeFavorite(existingFavorite);
                favoriteRepository.delete(existingFavorite);
                podcastUserRepository.save(podcastUser);
                episodeRepository.save(episode);
                return new ResponseEntity<>("Eliminado de Favoritos", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El episodio no está en favoritos", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("No se ha podido eliminar", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/favorite/removeArFav")
    public ResponseEntity<?> removeFavAr(Authentication authentication, @RequestParam Long id) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        Archive archive = archiveRepository.findById(id).orElse(null);
        if (podcastUser != null && archive != null) {
            FavoriteAr existingFavorite = podcastUser.getFavoriteArSet().stream()
                    .filter(fav -> fav.getArchiveFav().equals(archive))
                    .findFirst()
                    .orElse(null);
            if (existingFavorite != null) {
                podcastUser.removeArFavorite(existingFavorite);
                archive.removeArFavorite(existingFavorite);
                favoriteArRepository.delete(existingFavorite);
                podcastUserRepository.save(podcastUser);
                archiveRepository.save(archive);
                return new ResponseEntity<>("Eliminado de Favoritos", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El Archivo no está en favoritos", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("No se ha podido eliminar", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/episodes/favs")
    public Set<EpisodeDTO> getEpFavs(Authentication authentication){
        PodcastUser currentUser = podcastUserRepository.findByMail(authentication.getName());
        return currentUser.getFavoriteSet().stream().map(favorite -> favorite.getEpisodeFav()).map(episode -> new EpisodeDTO(episode)).collect(Collectors.toSet());
    }

    @GetMapping("/archives/favs")
    public Set<ArchiveDTO> getArFavs(Authentication authentication){
        PodcastUser currentUser = podcastUserRepository.findByMail(authentication.getName());
        return currentUser.getFavoriteArSet().stream().map(favorite -> favorite.getArchiveFav()).map(archive -> new ArchiveDTO(archive)).collect(Collectors.toSet());
    }
}