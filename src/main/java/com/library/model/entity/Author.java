package com.library.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Author extends ParentEntity {

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column
    private String surname;

    @NotNull
    @Column
    private LocalDate birthDate;

    @Column
    private LocalDate deathDate;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
