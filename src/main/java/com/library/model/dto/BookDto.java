package com.library.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDto extends ParentDto {

    private String title;

    private Short publicationYear;

    private Integer stack;

    private String unit;
    @NotEmpty
    private List<AuthorNameDto> authors;

    private PublisherTitleDto publisher;

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", stack=" + stack +
                ", unit='" + unit + '\'' +
                ", authors=" + authors +
                ", publisher=" + publisher +
                '}';
    }
}
