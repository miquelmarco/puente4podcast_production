package com.puente4podcast.puente4podcast.controllers;

import com.puente4podcast.puente4podcast.dtos.ArchiveDTO;
import com.puente4podcast.puente4podcast.models.*;
import com.puente4podcast.puente4podcast.repositories.ArchiveRepository;
import com.puente4podcast.puente4podcast.repositories.PodcastRepository;
import com.puente4podcast.puente4podcast.repositories.PodcastUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api")
public class ArchiveController {
    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private PodcastUserRepository podcastUserRepository;

    @Autowired
    private PodcastRepository podcastRepository;

    @GetMapping("/archives/featured")
    public List<ArchiveDTO> getFeatured() {
        return archiveRepository.findAll().stream().filter(Archive::isFeatured).map(ArchiveDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/archives/getArchives")
    public List<ArchiveDTO> getArchives() {
        return archiveRepository.findAll().stream().map(ArchiveDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/archives/newArchive")
    public ResponseEntity<?> newArchive(@RequestParam Byte archNumber, @RequestParam String archName, @RequestParam String archImg,
                                        @RequestParam String archLinkYt, @RequestParam String archLinkIVoox, @RequestParam String archDuration,
                                        @RequestParam String archDescription, Authentication authentication) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        if (archiveRepository.existsByArchiveNumber(archNumber)) {
            return new ResponseEntity<>("Número de archivo ya está usado!", HttpStatus.FORBIDDEN);
        }
        if (podcastUser.isAdmin()) {
            Archive newArchive = new Archive((byte) archNumber, archName, archImg, archLinkYt, archLinkIVoox, archDuration, "Archivo", archDescription, false);
            Podcast podcast = podcastRepository.findByName("puente4podcast");
            podcast.addArchive(newArchive);
            archiveRepository.save(newArchive);
            podcastRepository.save(podcast);
            return new ResponseEntity<>("Archivo Ingresado Chaval!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Sólo un admin puede ingresar un Archivo!", HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/modArFeatured")
    public ResponseEntity<?> modArFeatured(Authentication authentication, @RequestParam Long id) {
        if (podcastUserRepository.findByMail(authentication.getName()).isAdmin()) {
            Archive arFeatured = archiveRepository.findById(id).orElse(null);
            if (arFeatured == null) {
                return new ResponseEntity<>("Archivo no Existe", HttpStatus.NOT_FOUND);
            }
            if (arFeatured.isFeatured()) {
                arFeatured.setFeatured(false);
                archiveRepository.save(arFeatured);
                return new ResponseEntity<>("Destacado Retirado", HttpStatus.OK);
            }
            if (!arFeatured.isFeatured()) {
                arFeatured.setFeatured(true);
                archiveRepository.save(arFeatured);
                return new ResponseEntity<>("Destacado Agregado", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Faltan Atribuciones", HttpStatus.CONFLICT);
    }
}