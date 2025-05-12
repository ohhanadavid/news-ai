package com.NewsAI.newsAiGateway.jwt;


import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import reactor.core.publisher.Mono;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;



@Configuration
@EnableWebFluxSecurity
@Log4j2
public class SecurityConfig {

//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;
//    @Value("${keycloak.client-id}")
//    String clientId;
//    @Value("${keycloak.client-secret}")
//    String clientSecret;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5174","http://localhost:5175","http://localhost:8080"
                    ,"http://10.0.0.3:5173","http://10.0.0.3:5174","http://10.0.0.3:5175"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/",
                                "/index.html",
                                "/static/**",
                                "/fonts/**",
                                "/dist/**",
                                "/dashboard",
                                "/update",
                                "/background.png",
                                "/Images/**",
                                "/login.png",
                                "/login.webp",
                                "/SignIn.png",
                                "/update.png",
                                "/icon.jpeg",
                                "/icon.png",
                                "/assets/**",
                                "/favicon.ico",
                                "/manifest.json",
                                "/*.js",
                                "/*.css",
                                "/public/**",
                                "api/saveUser",
                                "api/authenticate",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/swagger-ui.html",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/webjars/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new ReactiveKeycloakJwtAuthenticationConverter())
                        )

                )    .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((exchange, ex) -> {
                            String url = exchange.getRequest().getURI().toString();
                            boolean token = exchange.getRequest().getHeaders().containsKey("Authorization");
                            log.error("401 Unauthorized error at URL: {} token? {}" , url,token);
                            log.info("401 Unauthorized error at URL: {} token? {}" , url,token);
                            return Mono.error(ex);
                        })
                        .accessDeniedHandler((exchange, ex) -> {
                            String url = exchange.getRequest().getURI().toString();
                            log.error("403 Forbidden error at URL: {}" , url);
                            log.info("403 Forbidden error at URL: {}" , url);
                            return Mono.error(ex);
                        })



                );

        return http.build();
    }


    public static class ReactiveKeycloakJwtAuthenticationConverter
            implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

        private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

        @Override
        public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
            return Mono.fromSupplier(() -> {
                Collection<GrantedAuthority> authorities = Stream.concat(
                        defaultConverter.convert(jwt).stream(),
                        extractResourceRoles(jwt).stream()
                ).collect(Collectors.toSet());

                return new JwtAuthenticationToken(jwt, authorities);
            });
        }

        private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toSet());
        }
    }
}