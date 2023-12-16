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

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class ComentaryController {
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private PodcastUserRepository podcastUserRepository;
    @Autowired
    private ComentaryEpRepository comentaryRepository;
    @Autowired
    private ComentaryArRepository comentaryArRepository;

    @GetMapping("/episodes/{id}")
    public EpisodeDTO getComentByEp(@PathVariable Long id) {
        return episodeRepository.findById(id).map(EpisodeDTO::new).orElse(null);
    }

    @GetMapping("/archives/{id}")
    public ArchiveDTO getComentByAr(@PathVariable Long id) {
        return archiveRepository.findById(id).map(ArchiveDTO::new).orElse(null);
    }

    @PostMapping("episodes/addComment")
    public ResponseEntity<?> addComment(Authentication authentication, String comment, Long id ) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        Episode episode = episodeRepository.findById(id).orElse(null);
        if (comment.isBlank()) {
            return new ResponseEntity<>("No has agregado un comentario", HttpStatus.FORBIDDEN);
        }
        if (podcastUser == null) {
            return new ResponseEntity<>("Debes estar logueado", HttpStatus.FORBIDDEN);
        }
        if (episode == null) {
            return new ResponseEntity<>("Episodio no encontrado", HttpStatus.FORBIDDEN);
        }
        ComentaryEp newCommentary = new ComentaryEp(comment, LocalDate.now());
        episode.addComentary(newCommentary);
        podcastUser.addComentary(newCommentary);
        comentaryRepository.save(newCommentary);
        episodeRepository.save(episode);
        podcastUserRepository.save(podcastUser);
        return new ResponseEntity<>("Comentario agregado", HttpStatus.OK);
    }

    @PostMapping("archives/addArComment")
    public ResponseEntity<?> addArComment(Authentication authentication, String comment, Long id ) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        Archive archive = archiveRepository.findById(id).orElse(null);
        if (comment.isBlank()) {
            return new ResponseEntity<>("No has agregado un comentario", HttpStatus.FORBIDDEN);
        }
        if (podcastUser == null) {
            return new ResponseEntity<>("Debes estar logueado", HttpStatus.FORBIDDEN);
        }
        if (archive == null) {
            return new ResponseEntity<>("Archivo no encontrado", HttpStatus.FORBIDDEN);
        }
        ComentaryAr newCommentary = new ComentaryAr(comment, LocalDate.now());
        archive.addArComentary(newCommentary);
        podcastUser.addArComentary(newCommentary);
        comentaryArRepository.save(newCommentary);
        archiveRepository.save(archive);
        podcastUserRepository.save(podcastUser);
        return new ResponseEntity<>("Comentario agregado", HttpStatus.OK);
    }
}