package com.NewsAI.newsAiGateway.configuration;
//
//
import com.NewsAI.newsAiGateway.DTO.UserData;
import com.NewsAI.newsAiGateway.DTO.UserRequest;
import com.NewsAI.newsAiGateway.DTO.UserRequestWithCategory;
import com.NewsAI.newsAiGateway.kafka.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;

import static com.NewsAI.newsAiGateway.kafka.KafkaTopic.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
//
@Configuration
@Log4j2
public class GatewayConfig {


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

    private final WebClient webClient;

    @Autowired
    public GatewayConfig() {
        this.webClient = WebClient.builder()
                .filter((request, next) -> {
                    log.debug("Request URL: {}", request.url());
                    return next.exchange(request);
                })
                .build();
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
        return RouterFunctions
                .route(POST("/authenticate"),this::handleRequestWithoutAuthorization)
                .andRoute(POST("/saveUser"),this::handleRequestWithoutAuthorization)
                .andRoute(PUT("/updateMail/{oldEmail}"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(PUT("/changePassword"), this::handleRequestToDataManagerWithBody)

                .andRoute(POST("/saveCategory"), this::handleRequestToDataManagerWithBody)
                .andRoute(GET("/getPreferenceByCategory"),this::handleRequestToDataManagerWithoutBody)
                .andRoute(GET("/myCategories"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(DELETE("/deletePreference"), this::handleRequestToDataManagerWithBody)
                .andRoute(DELETE("/deleteCategory"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(PUT("/changeCategory"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("/updatePreference"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("/updateCategory"),this::handleRequestToDataManagerWithBody)

                .andRoute(POST("/saveLanguage"), this::handleRequestToDataManagerWithBody)
                .andRoute(GET("/getMyLanguages"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(GET("/getLanguagesCode"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(DELETE("/deleteLanguage"), this::handleRequestToDataManagerWithBody)
                .andRoute(PUT("/updateLanguage/{email}"), this::handleRequestToDataManagerWithBody)

                .andRoute(GET("/getUser/{email}"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(DELETE("/deleteUser"), this::handleRequestToDataManagerWithoutBody)
                .andRoute(PUT("/updateName"),this::handleRequestToDataManagerWithBody)



                .andRoute(GET("/getLatestNews"), this::handleGetLatestNews)
                .andRoute(GET("/getLatestNewsByCategory"), this::handleGetLatestNewsWithCategory)
                .andRoute(GET("/getLatestListNewsByCategories"), this::handleGetLatestNewsWithMtCategories)

                .andRoute(GET("/getCategories"), this::handleRequestToNewsManagerWithoutBody)
                .andRoute(GET("/getLanguages"), this::handleRequestToNewsManagerWithoutBody)

                .andRoute(GET("/checkCategory"), this::handleRequestToNewsManagerWithoutBody)
                .andRoute(GET("/getLanguageCode"), this::handleRequestToNewsManagerWithoutBody)
                .andRoute(GET("/maximumLanguage"), this::handleRequestToNewsManagerWithoutBody)

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
        log.info("forwardDeleteRequest {} {} request send to {}",request.method(),request.uri(),path);
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
        log.info("forwardRequest {} {} request send to {}",request.method(),request.uri(),path);

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

        return request.bodyToMono(String.class)

                .flatMap(body -> {
                        UserRequest userRequest = new UserRequest();
                        UserData userData = getJwtToken();
                        userRequest.setUserID(userData.getUserID());
                        userRequest.setNumberOfArticles(
                                request.queryParam("numberOfArticles")
                                        .map(Integer::parseInt)
                                        .orElse(3)
                        );
                        try {
                            producer.send(userRequest, GET_LATEST_NEWS);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        log.info("handleGetLatestNews Sent message for {} to Kafka topic: {}",userData.getUserID(), GET_LATEST_NEWS);
                        return ServerResponse.ok().bodyValue("The news send");

                    });

    }

    private Mono<ServerResponse> handleGetLatestNewsWithCategory(ServerRequest request){

        return request.bodyToMono(String.class)

                .flatMap(body -> {
                    UserRequestWithCategory userRequest = new UserRequestWithCategory();
                    UserData userData = getJwtToken();
                    userRequest.setUserID(userData.getUserID());
                    userRequest.setNumberOfArticles(
                            request.queryParam("numberOfArticles")
                                    .map(Integer::parseInt)
                                    .orElse(3)
                    );
                    userRequest.setCategory(
                            request.queryParam("category")
                                    .orElseThrow(() -> new IllegalArgumentException("Category is required"))
                    );
                    try {
                        producer.send(userRequest, GET_LATEST_NEWS_BY_CATEGORY);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("handleGetLatestNewsWithCategory Sent message for {} to Kafka topic: {}",userData.getUserID() ,GET_LATEST_NEWS_BY_CATEGORY);
                    return ServerResponse.ok().bodyValue("The news send");

                });
    }

    private Mono<ServerResponse> handleGetLatestNewsWithMtCategories(ServerRequest request){

        return request.bodyToMono(String.class)

                .flatMap(body -> {
                    UserRequest userRequest = new UserRequest();
                    UserData userData = getJwtToken();
                    userRequest.setUserID(userData.getUserID());
                    userRequest.setNumberOfArticles(
                            request.queryParam("numberOfArticles")
                                    .map(Integer::parseInt)
                                    .orElse(3)
                    );
                    try {
                        producer.send(userRequest, GET_LATEST_NEWS_BY_MY_CATEGORIES);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("handleGetLatestNewsWithMtCategories Sent message to Kafka topic: {}", GET_LATEST_NEWS_BY_MY_CATEGORIES);
                    return ServerResponse.ok().bodyValue("The news send");

                });
    }

    public UserData getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return new UserData((Jwt) authentication.getPrincipal());
        }
        return null;
    }
}
