package com.data_manager.data_manager.configuration;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakConfig {

    // שדות סטטיים עם Lombok @Getter
    @Getter private static String serverUrl;
    @Getter private static String realm;
    @Getter private static String adminUsername;
    @Getter private static String adminPassword;
    @Getter private static String clientId;
    @Getter private static String clientSecret;
    @Getter private static String clientGrantType;


    public KeyCloakConfig(
            @Value("${keycloak.auth-server-url}") String serverUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.admin-user}") String adminUsername,
            @Value("${keycloak.admin-password}") String adminPassword,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret,
            @Value("${keycloak.client.grantType}") String clientGrantType) {

        KeyCloakConfig.serverUrl = serverUrl;
        KeyCloakConfig.realm = realm;
        KeyCloakConfig.adminUsername = adminUsername;
        KeyCloakConfig.adminPassword = adminPassword;
        KeyCloakConfig.clientId = clientId;
        KeyCloakConfig.clientSecret = clientSecret;
        KeyCloakConfig.clientGrantType = clientGrantType;
    }
}
