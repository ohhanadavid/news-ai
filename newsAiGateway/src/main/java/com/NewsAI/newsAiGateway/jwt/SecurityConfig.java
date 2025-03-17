package com.NewsAI.newsAiGateway.jwt;


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
import reactor.core.publisher.Mono;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;



@Configuration
@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//@KeycloakConfiguration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
    @Value("${keycloak.client-id}")
    String clientId;
    @Value("${keycloak.client-secret}")
    String clientSecret;

//
//    @Bean
//    public ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider() {
//        return ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
//                .clientCredentials()
//                .build();
//    }
//    @Bean
//    public ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager(
//            ReactiveClientRegistrationRepository clientRegistrationRepository,
//            ServerOAuth2AuthorizedClientRepository authorizedClientRepository,
//            ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider) {
//
//        DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
//
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return authorizedClientManager;
//    }
//
//    @Bean
//    public ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
//        return new WebSessionServerOAuth2AuthorizedClientRepository();
//    }
//
//
//
//    @Bean
//    public ReactiveJwtDecoder reactiveJwtDecoder() {
//        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
//    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/public/**",
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
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new ReactiveKeycloakJwtAuthenticationConverter())
                        )
                );

        return http.build();
    }


//
//    @Bean
//    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("keycloak")
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("openid")
//                .issuerUri(issuerUri)
//                .build();
//
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
//    }



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