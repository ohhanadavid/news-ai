package com.NewsAI.newsAiGateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
class GlobalDefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    ObjectMapper om;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> defaultErrorHandler( Exception e) throws Exception {
        logger.error("exception:{}, class:{}", e.getMessage(), e.getClass().getName());
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        if (e instanceof WebClientResponseException) {
            return new ResponseEntity<>(anErrorResponse(e.getMessage()), ((WebClientResponseException) e).getStatusCode());
//            String res ="";
//            return switch (((WebClientResponseException) e).getStatusCode()) {
//                case CONFLICT -> new ResponseEntity<>(anErrorResponse("Duplicate Id"), CONFLICT);
//                case NOT_FOUND -> new ResponseEntity<>(anErrorResponse("Not Found"), HttpStatus.NOT_FOUND);
//                case BAD_REQUEST -> {
//                    res = anErrorResponse(e.getMessage());
//                    yield new ResponseEntity<>(res, BAD_REQUEST);
//                }
//                case FORBIDDEN -> {
//                    res = anErrorResponse(e.getMessage());
//                    yield new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
//                }
//                case UNAUTHORIZED -> {
//                    res = anErrorResponse(e.getMessage());
//                    yield new ResponseEntity<>(res, UNAUTHORIZED);
//                }
//                default ->
//                        new ResponseEntity<>(anErrorResponse("unexpected problem"), ((WebClientResponseException) e).getStatusCode());
//            };
        }
        logger.error(e.getMessage(), e);
        // Otherwise it is unexpected
        return new ResponseEntity<>(anErrorResponse("unexpected problem"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String anErrorResponse(String s) {
        RestErrorMessage em = RestErrorMessage.RestErrorMessageBuilder.aRestErrorMessage().error(s).build();
        String res = null;
        try {
            res = om.writeValueAsString(em);
            return res;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }
}
