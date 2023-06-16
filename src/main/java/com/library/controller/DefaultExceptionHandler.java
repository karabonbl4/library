package com.library.controller;

import com.library.exception.MissingRequiredDataException;
import com.library.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(MissingRequiredDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingRequiredDataException(MissingRequiredDataException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParameterException(InvalidParameterException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }
}
