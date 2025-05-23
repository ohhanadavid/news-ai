package com.data_manager.data_manager.Exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
class GlobalDefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    ObjectMapper om;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
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
        if (e instanceof DuplicateKeyException) {
            return new ResponseEntity<>(anErrorResponse("Duplicate Id"), CONFLICT);
        } else if (e instanceof EmptyResultDataAccessException|| e instanceof NotFoundException) {
            return new ResponseEntity<>(anErrorResponse("Not Found"), HttpStatus.NOT_FOUND);
            // we raise runtime exceptions for business issues.
        } else if (e instanceof MissingServletRequestParameterException|| e instanceof BadRequestException) {
            String res = anErrorResponse(e.getMessage());
            return new ResponseEntity<>(res, BAD_REQUEST);
        } else if (e instanceof AccessDeniedException || e instanceof ForbiddenException) {
            String res = anErrorResponse(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        } else if (e instanceof ItemFoundException) {
            return new ResponseEntity<>(anErrorResponse("Duplicate Email"), CONFLICT);
        } else if (e instanceof ItemNotFoundException) {
            if (e.getMessage().isEmpty())
                return new ResponseEntity<>(anErrorResponse("Not Found"), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(anErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }else if (e instanceof BadCredentialsException||e instanceof NotAuthorizedException) {
            return new ResponseEntity<>(anErrorResponse("Wrong password"), HttpStatus.UNAUTHORIZED);
        } else if (e instanceof MoreThenAllowException) {
            return new ResponseEntity<>(anErrorResponse(e.getMessage()), BAD_REQUEST);
        } else if (e instanceof HttpClientErrorException) {
            return  new ResponseEntity<>(anErrorResponse(e.getMessage()), ((HttpClientErrorException) e).getStatusCode());

        } else if (e instanceof HttpServerErrorException) {
            return  new ResponseEntity<>(anErrorResponse(e.getMessage() ), ((HttpServerErrorException) e).getStatusCode());

        } else if (e.getCause() instanceof BadCredentialsException) {
            return new ResponseEntity<>(anErrorResponse("Wrong password"), HttpStatus.UNAUTHORIZED);
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
