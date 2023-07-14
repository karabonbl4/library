package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorNameDto extends ParentDto {

    private String fullName;

    @Override
    public String toString() {
        return "AuthorNameDto{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
