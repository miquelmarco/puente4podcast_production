package com.puente4podcast.puente4podcast.controllers;

import com.puente4podcast.puente4podcast.dtos.PodcastUserDTO;
import com.puente4podcast.puente4podcast.models.Podcast;
import com.puente4podcast.puente4podcast.models.PodcastUser;
import com.puente4podcast.puente4podcast.repositories.PodcastRepository;
import com.puente4podcast.puente4podcast.repositories.PodcastUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PodcastUserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PodcastUserRepository podcastUserRepository;
    @Autowired
    PodcastRepository podcastRepository;

    @GetMapping("/getCurrent")
    public PodcastUserDTO getCurrent(Authentication authentication) {
        return new PodcastUserDTO(podcastUserRepository.findByMail(authentication.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerPodcastUser(@RequestParam String firstName, @RequestParam String lastName,
                                                      @RequestParam String userName, @RequestParam String mail,
                                                      @RequestParam String nacionality, @RequestParam String password) {
        if (password.isBlank()) {
            return new ResponseEntity<>("Ingresa tu contraseña", HttpStatus.FORBIDDEN);
        }
        if (password.length() < 8) {
            return new ResponseEntity<>("La contraseña debe tener al menos 8 caracteres", HttpStatus.FORBIDDEN);
        }
        if (firstName.isBlank()) {
            return new ResponseEntity<>("Ingresa tu nombre", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Ingresa tu apellido", HttpStatus.FORBIDDEN);
        }
        if (userName.isBlank()) {
            return new ResponseEntity<>("Ingresa tu nombre de usuario", HttpStatus.FORBIDDEN);
        }
        if (mail.isBlank()) {
            return new ResponseEntity<>("Ingresa tu mail", HttpStatus.FORBIDDEN);
        }
        if (nacionality.isBlank()) {
            return new ResponseEntity<>("Indica tu nacionalidad", HttpStatus.FORBIDDEN);
        }
        if (podcastUserRepository.findByMail(mail) != null) {
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.FORBIDDEN);
        }
        if (podcastUserRepository.findByUserName(userName) != null) {
            return new ResponseEntity<>("Nombre de usuario en uso", HttpStatus.FORBIDDEN);
        }
        Podcast podcast = podcastRepository.findByName("puente4podcast");
        PodcastUser podcastUser = new PodcastUser(firstName, lastName, userName, mail, nacionality, passwordEncoder.encode(password), false, podcast);

        podcastUserRepository.save(podcastUser);
        return new ResponseEntity<>("Usuario creado", HttpStatus.CREATED);
    }

    @PatchMapping("/modPass")
    public ResponseEntity<?> modPass(Authentication authentication, @RequestParam String actualPass, String newPass) {
        PodcastUser podcastUser = podcastUserRepository.findByMail(authentication.getName());
        if (!actualPass.isEmpty() && !newPass.isEmpty()) {
            if (podcastUser != null) {
                if (passwordEncoder.matches(actualPass, podcastUser.getPassword())) {
                    podcastUser.setPassword(passwordEncoder.encode(newPass));
                    podcastUserRepository.save(podcastUser);
                    return new ResponseEntity<>("Contraseña modificada!", HttpStatus.OK);
                }
                return new ResponseEntity<>("Contraseña no modificada", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Main error", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<Object> registerAdmin(@RequestParam String firstName, @RequestParam String lastName,
                                                @RequestParam String userName, @RequestParam String mail,
                                                @RequestParam String nacionality, @RequestParam String password) {
        if (password.isBlank()) {
            return new ResponseEntity<>("Ingresa Contraseña", HttpStatus.FORBIDDEN);
        }
        if (firstName.isBlank()) {
            return new ResponseEntity<>("Ingresa Nombre", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Ingresa Apellido", HttpStatus.FORBIDDEN);
        }
        if (userName.isBlank()) {
            return new ResponseEntity<>("Ingresa Nombre de Usuario", HttpStatus.FORBIDDEN);
        }
        if (mail.isBlank()) {
            return new ResponseEntity<>("Ingresa Mail", HttpStatus.FORBIDDEN);
        }
        if (nacionality.isBlank()) {
            return new ResponseEntity<>("Indica Nacionalidad", HttpStatus.FORBIDDEN);
        }
        if (podcastUserRepository.findByMail(mail) != null) {
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.FORBIDDEN);
        }
        if (podcastUserRepository.findByUserName(userName) != null) {
            return new ResponseEntity<>("Nombre de usuario en uso", HttpStatus.FORBIDDEN);
        }
        Podcast podcast = podcastRepository.findByName("puente4podcast");
        PodcastUser podcastUser = new PodcastUser(firstName, lastName, userName, mail, nacionality, passwordEncoder.encode(password), true, podcast);

        podcastUserRepository.save(podcastUser);
        return new ResponseEntity<>("Administrador Creado", HttpStatus.CREATED);
    }

    @GetMapping("/podcastUsers/getAdmins")
    public Set<PodcastUserDTO> getAdmins() {
        if (podcastUserRepository == null) {
            return Collections.emptySet();
        }
        try {
            return podcastUserRepository.findAll()
                    .stream()
                    .filter(PodcastUser::isAdmin)
                    .map(PodcastUserDTO::new)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @PatchMapping("/podcastUsers/giveAdmin")
    public ResponseEntity<?> giveAdmin(@RequestParam String giveAdminMail, Authentication authentication) {
        PodcastUser loggedInUser = podcastUserRepository.findByMail(authentication.getName());

        if (loggedInUser != null && loggedInUser.isAdmin()) {
            PodcastUser userToPromote = podcastUserRepository.findByMail(giveAdminMail);

            if (userToPromote != null) {
                if (userToPromote.isAdmin()) {
                    return new ResponseEntity<>("Ususario ya es Administrador", HttpStatus.CONFLICT);
                }
                userToPromote.setAdmin(true);
                podcastUserRepository.save(userToPromote);
                return new ResponseEntity<>("Admin Establecido", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No tienes autorización para establecer administradores", HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/podcastUsers/deleteAdmin")
    public ResponseEntity<?> deleteAdmin(@RequestParam String deleteAdminMail, Authentication authentication) {
        PodcastUser loggedInUser = podcastUserRepository.findByMail(authentication.getName());

        if (loggedInUser != null && loggedInUser.isAdmin()) {
            PodcastUser userToRemoveAdmin = podcastUserRepository.findByMail(deleteAdminMail);

            if (userToRemoveAdmin != null) {
                if (!userToRemoveAdmin.isAdmin()) {
                    return new ResponseEntity<>("Este usuario no es administrador", HttpStatus.CONFLICT);
                }
                userToRemoveAdmin.setAdmin(false);
                podcastUserRepository.save(userToRemoveAdmin);
                return new ResponseEntity<>("Rol de administrador eliminado", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No tienes autorización para quitar roles de administrador", HttpStatus.FORBIDDEN);
        }
    }
}
