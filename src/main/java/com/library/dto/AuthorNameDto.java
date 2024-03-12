package com.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AuthorNameDto extends ParentDto {

    private String fullName;
}
