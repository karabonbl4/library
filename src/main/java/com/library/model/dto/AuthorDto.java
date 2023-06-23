package com.library.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto extends AuthorNameDto {

    private LocalDate birthDate;

    private LocalDate deathDate;

    private List<BookTitleDto> books;
}
