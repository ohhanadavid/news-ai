package com.data_manager.data_manager.jwt;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@KeycloakConfiguration
public class SecurityConfig {

//    @Bean
//    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
//        KeycloakAuthenticationProvider provider = new KeycloakAuthenticationProvider();
//        provider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        return provider;
//    }
//
//    @Bean
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
        return new KeycloakAuthenticationProvider();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**",
                                "/saveUser",
                                "/authenticate",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/swagger-ui.html",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())
                        )
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );



        return http.build();
    }
}