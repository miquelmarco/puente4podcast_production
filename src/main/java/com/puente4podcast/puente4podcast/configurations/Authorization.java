package com.puente4podcast.puente4podcast.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class Authorization {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers("/web/**").permitAll()
                .antMatchers("/static/index.html").permitAll()
                .antMatchers("/static/admin.html").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/archives/featured").permitAll()
                .antMatchers(HttpMethod.GET, "/api/archives/getArchives").permitAll()
                .antMatchers(HttpMethod.POST, "/api/archives/newArchive").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/episodes/newEpisode").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/modArFeatured").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/modEpFeatured").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/deleteEp/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/deleteAr/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/episodes/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/episodes").permitAll()
                .antMatchers(HttpMethod.GET, "/api/episodes/featured").permitAll()
                .antMatchers(HttpMethod.GET, "/api/archives/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/episodes/addComment").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/archives/addArComment").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/favorite/addEpFav").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/favorite/addArFav").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/favorite/removeEpFav").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/favorite/removeArFav").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/episodes/favs").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/archives/favs").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/getCurrent").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/modPass").hasAnyAuthority("PUSER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/registerAdmin").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/podcastUsers/getAdmins").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/podcastUsers/giveAdmin").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/podcastUsers/deleteAdmin").hasAuthority("ADMIN")
                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("mail")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}