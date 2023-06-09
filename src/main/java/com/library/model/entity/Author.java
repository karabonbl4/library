package com.library.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

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

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate birthDay;

    @Column(name = "date_of_death")
    private LocalDate ripDay;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
