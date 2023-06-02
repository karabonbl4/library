package com.library.model.dto;

import com.library.model.mapper.shortDto.AuthorFullNameDto;
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

    private List<AuthorFullNameDto> authors;

//    @Builder
//    public BookDto(Long id, Boolean deleted, String title, Short publicationYear, Integer stack, String unit, List<AuthorFullNameDto> authors) {
//        super(id, deleted);
//        this.title = title;
//        this.publicationYear = publicationYear;
//        this.stack = stack;
//        this.unit = unit;
//        this.authors = authors;
//    }
}
