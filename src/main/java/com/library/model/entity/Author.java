package com.library.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author extends ParentEntity {

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column
    private String surname;

    @NotBlank
    @Column(name = "date_of_birth")
    private LocalDate birthDay;

    @Column(name = "date_of_death")
    private LocalDate ripDay;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
