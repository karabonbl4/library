package com.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class BookDto extends ParentDto {

    private String title;

    private Short publicationYear;

    private Integer stack;

    private String unit;

    private Boolean taken;

    private String inventoryNumber;

    private List<BookEmbeddedDto> referenceBooks;

    @NotEmpty
    private List<AuthorNameDto> authors;

    private PublisherTitleDto publisher;
}
