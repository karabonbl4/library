package com.library.model.dto;

import com.library.model.entity.AuthorStored;
import com.library.model.entity.BookShelf;
import com.library.model.entity.PublisherStored;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookStoredDto {

    private String id;

    private String title;

    private Short publicationYear;

    private String inventoryNumber;

    private BookShelf bookShelf;

    private PublisherStored publisher;

    private List<AuthorStored> authors;
}
