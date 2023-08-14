package com.library.model.entity.mongo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class StoredBook {

    @MongoId
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    private String title;

    private Short publicationYear;

    private BookShelf bookshelf;

    private PublisherTitleDto publisher;

    private List<AuthorNameDto> authors;
}
