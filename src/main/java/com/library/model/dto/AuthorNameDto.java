package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorNameDto extends ParentDto {

    private String fullName;
}
