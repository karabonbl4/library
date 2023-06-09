package com.library.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto extends ParentDto {

    private String fullName;

    private LocalDate birthDay;

    private LocalDate ripDay;

    private List<String> booksTitle;
}
