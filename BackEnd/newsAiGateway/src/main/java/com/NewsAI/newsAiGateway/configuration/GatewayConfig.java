package com.NewsAI.newsAiGateway.configuration;
//
//
import com.NewsAI.newsAiGateway.DTO.SendOption;
import com.NewsAI.newsAiGateway.DTO.UserData;
import com.NewsAI.newsAiGateway.DTO.UserRequest;
import com.NewsAI.newsAiGateway.DTO.UserRequestWithCategory;
import com.NewsAI.newsAiGateway.kafka.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.concurrent.TimeUnit;

import static com.NewsAI.newsAiGateway.kafka.KafkaTopic.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//
@Configuration
@Log4j2
public class GatewayConfig implements WebFluxConfigurer {


    @Value("${DATA_MANAGER_URL}")
    private String dataManagerURL;
    @Value("${DATA_MANAGER_PORT}")
    private String dataManagerPort;
    @Value("${NEWS_MANAGER_URL}")
    private String newsManagerURL;
    @Value("${NEWS_MANAGER_PORT}")
    private String newsManagerPort;

    @Autowired
    Producer producer;

    private final WebClient webClient  ;

    @Autowired
    public GatewayConfig() {
        this.webClient = WebClient.builder()
                .filter((request, next) -> {
                    log.debug("Request URL: {}", request.url());
                    return next.exchange(request);
                })
                .build();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // טיפול בקבצים סטטיים
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
    @PostConstruct
    public void logConfiguration() {
        log.info("=== Gateway Configuration ===");
        log.info("Data Manager URL: {}", dataManagerURL);
        log.info("News Manager URL: {}", newsManagerURL);
        log.info("WebClient initialized: {}", webClient != null);
        log.info("============================");
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        log.info("in RouterFunction<ServerResponse> gatewayRoutes()");
        log.info("data url: {}",dataManagerURL);
        log.info("news url: {}",newsManagerURL);
        return
                route(RequestPredicates.GET("/{path:^(?!api|v3|swagger-ui|assets|fonts).*$}"),
                        req -> ServerResponse.ok().contentType(MediaType.TEXT_HTML)
                                .bodyValue(new ClassPathResource("static/index.html")))
                        .andRoute(RequestPredicates.GET("/"),
                                req -> ServerResponse.ok().contentType(MediaType.TEXT_HTML)
                                        .bodyValue(new ClassPathResource("static/index.html")))


                .andRoute(POST("api/authenticate"),this::handleRequestWithoutAuthorization)
                .andRoute(POST("api/saveUser"),this::handleRequestWithoutAuthorization)
                .andRoute(PUT("api/updateUser"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("api/changePassword"), this::handleRequestToDataManagerWithBody)
                .andRoute(POST("api/refreshToken"), this::handleRequestToDataManagerWithBody)

                .andRoute(POST("api/saveCategory"), this::handleRequestToDataManagerWithBody)
                .andRoute(GET("api/getPreferenceByCategory"),this::handleRequestToDataManagerWithoutBody)
                .andRoute(GET("api/myCategories"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(DELETE("api/deletePreference"), this::handleRequestToDataManagerWithBody)
                .andRoute(DELETE("api/deleteCategory"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(PUT("api/changeCategory"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("api/updatePreference"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("api/updateCategory"),this::handleRequestToDataManagerWithBody)

                .andRoute(POST("api/saveLanguage"), this::handleRequestToDataManagerWithBody)
                .andRoute(GET("api/getMyLanguages"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(GET("api/getMyLanguagesCode"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(DELETE("api/deleteLanguage"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("api/updateLanguage"), this::handleRequestToDataManagerWithBody)

                .andRoute(GET("api/getUser"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(DELETE("api/deleteUser"), this::handleRequestToDataManagerWithoutBody)

                .andRoute(GET("api/getLatestNews"), this::handleGetLatestNews)
                .andRoute(GET("api/getLatestNewsByCategory"), this::handleGetLatestNewsWithCategory)
                .andRoute(GET("api/getLatestListNewsByCategories"), this::handleGetLatestNewsWithMtCategories)

                .andRoute(GET("api/getCategories"), this::handleRequestToNewsManagerWithoutBody)
                .andRoute(GET("api/getLanguages"), this::handleRequestToNewsManagerWithoutBody)

                .andRoute(GET("api/checkCategory"), this::handleRequestToNewsManagerWithoutBody)
                .andRoute(GET("api/getLanguageCode"), this::handleRequestToNewsManagerWithoutBody)
                .andRoute(GET("api/maximumLanguage"), this::handleRequestToNewsManagerWithoutBody)

                .andRoute(GET("/test"), request -> {
                    log.info("Handling test request");
                    return ServerResponse.ok().bodyValue("Gateway is working");
                });
    }

    private String buildURI (ServerRequest request,String host,String port){
        return UriComponentsBuilder.fromUri(request.uri()).host(host).port(port).toUriString();
    }

    private Mono<ServerResponse> handleRequestToDataManagerWithBody(ServerRequest request){
            return forwardRequestWithBody(request,dataManagerURL,dataManagerPort);
    }

    private Mono<ServerResponse> handleRequestWithoutAuthorization(ServerRequest request){
        return forwardRequestWithoutAuthorization(request,dataManagerURL,dataManagerPort);
    }

    private Mono<ServerResponse> handleRequestToDataManagerWithoutBody(ServerRequest request){
        return forwardRequest(request,dataManagerURL,dataManagerPort);
    }

    private Mono<ServerResponse> handleRequestToNewsManagerWithoutBody(ServerRequest request){
        return forwardRequest(request,newsManagerURL,newsManagerPort);
    }

    private Mono<ServerResponse> forwardRequest(ServerRequest request,String host,String port) {
        String path = buildURI(request,host,port);
        log.info("forwardRequest {} {} request send to {}",request.method(),request.uri(),path);
        return webClient
                .method(request.method())
                .uri(path)
                .headers(headers -> headers.set("Authorization", request.headers().firstHeader("Authorization")))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
//                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> forwardRequestWithBody(ServerRequest request,String host,String port) {

        String path=buildURI(request,host,port);
        log.info("forwardRequestWithBody {} {} request send to {}",request.method(),request.uri(),path);

        return request.bodyToMono(String.class)
                .flatMap(body -> webClient
                        .method(request.method())
                        .uri(path)
                        .headers(headers -> headers.set("Authorization", request.headers().firstHeader("Authorization")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(String.class))
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
//                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> forwardRequestWithoutAuthorization(ServerRequest request,String host,String port) {
        String path=buildURI(request,host,port);
        log.info("forwardRequestWithoutAuthorization {} {} request send to {}",request.method(),request.uri(),path);

            return request.bodyToMono(String.class)
                    .flatMap(body -> webClient
                            .method(request.method())
                            .uri(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(body)
                            .retrieve()
                            .bodyToMono(String.class))
                    .flatMap(response -> ServerResponse.ok().bodyValue(response));
                   // .onErrorResume(this::handleError);

    }


    private Mono<ServerResponse> handleGetLatestNews(ServerRequest request) {
        return getJwtToken(request)
                .flatMap(userData -> {
                    UserRequest userRequest = new UserRequest();
                    userRequest.setUserID(userData.getUserID());
                    userRequest.setToken(userData.getToken());
                    userRequest.setNumberOfArticles(
                            request.queryParam("numberOfArticles").map(Integer::parseInt).orElse(3)
                    );
                    userRequest.setOption(
                            SendOption.valueOf(request.queryParam("sendOption")
                                    .orElse(String.valueOf(SendOption.EMAIL)).toUpperCase())
                    );

                    return Mono.fromRunnable(() -> {
                                try {
                                    producer.send(userRequest, GET_LATEST_NEWS);
                                    log.info("Sent message for {} to Kafka topic: {}", userData.getUserID(), GET_LATEST_NEWS);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException("Failed to serialize message", e);
                                }
                            })
                            .then(ServerResponse.ok().bodyValue("The news was sent successfully"))
                            .onErrorResume(e -> {
                                log.error("Error sending message to Kafka", e);
                                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .bodyValue("Failed to send news");
                            });
                });


    }

    private Mono<ServerResponse> handleGetLatestNewsWithCategory(ServerRequest request){
        return getJwtToken(request)
                .flatMap(userData -> {
                    UserRequestWithCategory userRequest = new UserRequestWithCategory();
                    userRequest.setUserID(userData.getUserID());
                    userRequest.setToken(userData.getToken());
                    userRequest.setNumberOfArticles(
                            request.queryParam("numberOfArticles").map(Integer::parseInt).orElse(3)
                    );
                    userRequest.setCategory(
                            request.queryParam("category")
                                    .orElseThrow(() -> new IllegalArgumentException("Category is required"))
                    );
                    userRequest.setOption(
                            SendOption.valueOf(request.queryParam("sendOption")
                                    .orElse(String.valueOf(SendOption.EMAIL)).toUpperCase())
                    );

                    return Mono.fromRunnable(() -> {
                                try {
                                    producer.send(userRequest, GET_LATEST_NEWS_BY_CATEGORY);
                                    log.info("Sent message for {} to Kafka topic: {}", userData.getUserID(), GET_LATEST_NEWS_BY_CATEGORY);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException("Failed to serialize message", e);
                                }
                            })
                            .then(ServerResponse.ok().bodyValue("The news was sent successfully"))
                            .onErrorResume(e -> {
                                log.error("Error sending message to Kafka", e);
                                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .bodyValue("Failed to send news");
                            });
                });

    }

    private Mono<ServerResponse> handleGetLatestNewsWithMtCategories(ServerRequest request){
        return getJwtToken(request)
                .flatMap(userData -> {
                    UserRequest userRequest = new UserRequest();
                    userRequest.setUserID(userData.getUserID());
                    userRequest.setToken(userData.getToken());
                    userRequest.setNumberOfArticles(
                            request.queryParam("numberOfArticles").map(Integer::parseInt).orElse(3)
                    );
                    userRequest.setOption(
                            SendOption.valueOf(request.queryParam("sendOption")
                                    .orElse(String.valueOf(SendOption.EMAIL)).toUpperCase())
                    );

                    return Mono.fromRunnable(() -> {
                                try {
                                    producer.send(userRequest, GET_LATEST_NEWS_BY_MY_CATEGORIES);
                                    log.info("Sent message for {} to Kafka topic: {}", userData.getUserID(), GET_LATEST_NEWS_BY_MY_CATEGORIES);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException("Failed to serialize message", e);
                                }
                            })
                            .then(ServerResponse.ok().bodyValue("The news was sent successfully"))
                            .onErrorResume(e -> {
                                log.error("Error sending message to Kafka", e);
                                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .bodyValue("Failed to send news");
                            });
                });

    }

    Mono<UserData> getJwtToken(ServerRequest request) {
        return request.principal()
                .cast(JwtAuthenticationToken.class)
                .map(auth -> new UserData((Jwt) auth.getToken()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid JWT token")));
    }
}
