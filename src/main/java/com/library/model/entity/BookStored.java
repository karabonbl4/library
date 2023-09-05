package com.library.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
@Getter
@Setter
public class BookStored {

    @MongoId
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    private String title;

    private Short publicationYear;

    private BookShelf bookShelf;

    private String inventoryNumber;

    private PublisherStored publisher;

    private List<AuthorStored> authors;

    private List<String> referenceBooks;
}
