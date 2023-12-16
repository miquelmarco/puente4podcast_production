package com.puente4podcast.puente4podcast.controllers;

import com.puente4podcast.puente4podcast.dtos.EpisodeDTO;
import com.puente4podcast.puente4podcast.models.*;
import com.puente4podcast.puente4podcast.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EpisodeController {
    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private PodcastUserRepository podcastUserRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private ComentaryArRepository comentaryArRepository;
    @Autowired
    private ComentaryEpRepository comentaryEpRepository;

    @GetMapping("/episodes")
    public List<EpisodeDTO> getAllEps() {
        return episodeRepository.findAll().stream().map(EpisodeDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/episodes/featured")
    public List<EpisodeDTO> getFeatured() {
        return episodeRepository.findAll().stream().filter(Episode::isFeatured).map(EpisodeDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/episodes/newEpisode")
    public ResponseEntity<?> newEpisode(@RequestParam Byte epSeason, @RequestParam Byte epEpisode,
                                        @RequestParam String epName, @RequestParam String epImg,
                                        @RequestParam String epLinkYt, @RequestParam String epLinkIVoox,
                                        @RequestParam String epDuration, @RequestParam String epDescription,
                                        @RequestParam boolean epFeatured, Authentication authentication) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        if (epSeason == null
                || epEpisode == null
                || epName.isBlank()) {
            return new ResponseEntity<>("Datos Erróneos", HttpStatus.FORBIDDEN);
        }
        if (podcastUser.isAdmin()) {
            Episode newEpisode = new Episode(epSeason, epEpisode, epName,
                    epImg, epLinkYt, epLinkIVoox, epDuration,
                    "Canon", epDescription, epFeatured);
            Season getSeason = seasonRepository.findByNumber(epSeason);
            if (getSeason != null) {
                getSeason.addEpisode(newEpisode);
                episodeRepository.save(newEpisode);
            } else {
                Season season = new Season(epSeason);
                Podcast podcast = podcastRepository.findByName("puente4podcast");
                seasonRepository.save(season);
                podcast.addSeason(season);
                season.addEpisode(newEpisode);
                episodeRepository.save(newEpisode);
            }
            return new ResponseEntity<>("Episodio Ingresado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sólo un admin puede ingresar un episodio!", HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/modEpFeatured")
    public ResponseEntity<?> modEpFeatured(Authentication authentication, @RequestParam Long id) {
        if (podcastUserRepository.findByMail(authentication.getName()).isAdmin()) {
            Episode epFeatured = episodeRepository.findById(id).orElse(null);
            if (epFeatured == null) {
                return new ResponseEntity<>("Episodio no Existe", HttpStatus.NOT_FOUND);
            }
            if (epFeatured.isFeatured()) {
                epFeatured.setFeatured(false);
                episodeRepository.save(epFeatured);
                return new ResponseEntity<>("Destacado Retirado", HttpStatus.OK);
            }
            if (!epFeatured.isFeatured()) {
                epFeatured.setFeatured(true);
                episodeRepository.save(epFeatured);
                return new ResponseEntity<>("Destacado Agregado", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Faltan Atribuciones", HttpStatus.CONFLICT);
    }



    @DeleteMapping("/deleteEp/{id}")
    public ResponseEntity<?> deleteEp(Authentication authentication, @PathVariable Long id) {
        if (podcastUserRepository.findByMail(authentication.getName()).isAdmin()) {
            Episode ep = episodeRepository.findById(id).orElse(null);
            if (ep == null) {
                return new ResponseEntity<>("Episodio no Existe", HttpStatus.FORBIDDEN);
            }
            episodeRepository.delete(ep);
            return new ResponseEntity<>("Episodio Borrado", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No autorizado para realizar esta acción", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/deleteAr/{id}")
    public ResponseEntity<?> deleteAr(Authentication authentication, @PathVariable Long id) {
        if (podcastUserRepository.findByMail(authentication.getName()).isAdmin()) {
            Archive ar = archiveRepository.findById(id).orElse(null);
            if (ar == null) {
                return new ResponseEntity<>("Archivo no Existe", HttpStatus.FORBIDDEN);
            }
            archiveRepository.delete(ar);
            return new ResponseEntity<>("Archivo Borrado", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No autorizado para realizar esta acción", HttpStatus.FORBIDDEN);
    }
}