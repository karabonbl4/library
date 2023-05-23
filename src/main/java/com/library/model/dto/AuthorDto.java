package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto extends CustomDto{

    private String name;

    private String surname;

    private String fullName;

    private LocalDate birthDay;

    private LocalDate ripDay;

}
