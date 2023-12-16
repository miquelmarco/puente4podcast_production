package com.puente4podcast.puente4podcast.configurations;

import com.puente4podcast.puente4podcast.models.PodcastUser;
import com.puente4podcast.puente4podcast.repositories.PodcastUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Authentication extends GlobalAuthenticationConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private PodcastUserRepository podcastUserRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName -> {
            PodcastUser podcastUser = podcastUserRepository.findByMail(inputName);
            if (podcastUser != null) {
                if (podcastUser.isAdmin()) {
                    return new User(podcastUser.getMail(), podcastUser.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    return new User(podcastUser.getMail(), podcastUser.getPassword(),
                            AuthorityUtils.createAuthorityList("PUSER"));
                }
            } else {
                throw new UsernameNotFoundException("Unknown user:" + inputName);
            }
        });
    }
}