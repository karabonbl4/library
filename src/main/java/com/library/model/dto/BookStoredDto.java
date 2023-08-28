package com.library.model.dto;

import com.library.model.entity.BookShelf;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookStoredDto {

    private String id;

    private String title;

    private Short publicationYear;

    private BookShelf bookShelf;

    private PublisherTitleDto publisher;

    private List<AuthorNameDto> authors;
}
