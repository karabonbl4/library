package com.library.model.entity.mongo;

import com.library.model.dto.AuthorNameDto;
import com.library.model.dto.PublisherTitleDto;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
@Getter
@Setter
public class Book {

    @MongoId
    private ObjectId id;

    private Long sqlId;

    private String title;

    private Short publicationYear;

    private Bookshelf bookshelf;

    private PublisherTitleDto publisher;

    private List<AuthorNameDto> authors;
}
