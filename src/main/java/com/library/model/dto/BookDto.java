package com.library.model.dto;

import com.library.model.dto.embedded.EmbeddedAuthorDto;
import com.library.model.dto.embedded.EmbeddedPublisherDto;
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

    private List<EmbeddedAuthorDto> authors;

    private EmbeddedPublisherDto publisher;
}
