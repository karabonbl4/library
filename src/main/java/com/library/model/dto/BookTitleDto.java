package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class BookTitleDto extends ParentDto {

    private String title;

    private List<AuthorNameDto> authors;
}
