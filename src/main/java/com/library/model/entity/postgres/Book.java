package com.library.model.entity.postgres;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Book extends ParentEntity {

    @NotBlank
    @Column
    private String title;

    @NotNull
    @Column(name = "publication_year")
    private Short publicationYear;

    @NotNull
    @Column
    private Integer stack;

    @NotBlank
    @Column
    private String unit;

    @NotNull
    @JoinColumn(name = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @NotEmpty
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

}
