package com.library.service;

import com.library.model.entity.ParentEntity;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Component
public interface ValidateService<T extends ParentEntity> {

    default void validateId(Long id){
        if (id == null || id < 1L) {
            throw new InvalidParameterException("id must be 1 or more");
        }
    }

    void validateEntity(T t);
}
