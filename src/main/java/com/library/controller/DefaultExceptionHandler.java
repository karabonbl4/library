package com.library.controller;

import com.library.model.dto.ResponseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import java.net.ConnectException;
import java.time.LocalDateTime;

import static com.library.constant.ApplicationConstant.ENTITY_NOT_FOUND;
import static com.library.constant.ApplicationConstant.FIELD_NOT_PRESENT;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

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
        return buildErrorResponseValidationNotPresent(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return buildErrorResponseValidationNotPresent(e);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return buildErrorResponseByException(e);
    }

    private ResponseException buildErrorResponseByException(Exception e){
        ResponseException errorResponse = new ResponseException(e.getMessage(), LocalDateTime.now());
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    private ResponseException buildErrorResponseValidationNotPresent(Exception e){
        ResponseException errorResponse = new ResponseException(FIELD_NOT_PRESENT, LocalDateTime.now());
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }
}
