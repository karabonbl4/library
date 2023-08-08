package com.library.model.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Book {

    @MongoId
    private Long id;

    private String title;

    private Short publicationYear;

    private Integer stack;

    private String unit;

    private Publisher publisher;

    private List<Author> authors;
}
