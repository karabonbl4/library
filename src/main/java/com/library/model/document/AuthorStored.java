package com.library.model.document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuthorStored {

    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private LocalDate deathDate;
}
