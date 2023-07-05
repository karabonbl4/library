package com.library.controller;

import com.library.model.dto.ResponseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import java.net.ConnectException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    private final String ENTITY_NOT_FOUND = "Entity not found!";

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handleNotFoundException(EntityNotFoundException e) {
        ResponseException errorResponse = new ResponseException(ENTITY_NOT_FOUND, LocalDateTime.now());
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleIllegalArgumentException(IllegalArgumentException e) {
        return buildErrorResponseByException(e);
    }

    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handleConnectException(ConnectException e){
        return buildErrorResponseByException(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleConstraintViolationException(ConstraintViolationException e){
        return buildErrorResponseByException(e);
    }

    private ResponseException buildErrorResponseByException(Exception e){
        ResponseException errorResponse = new ResponseException(e.getMessage(), LocalDateTime.now());
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }
}
