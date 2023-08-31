package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookStoredTitleDto {

    private String id;

    private String title;

    private List<AuthorNameDto> authors;
}
