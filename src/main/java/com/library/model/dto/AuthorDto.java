package com.library.model.dto;

import com.library.model.mapper.shortDto.BookTitleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AuthorDto extends ParentDto {

    private String fullName;

    private LocalDate birthDay;

    private LocalDate ripDay;

    private List<BookTitleDto> books;

}
