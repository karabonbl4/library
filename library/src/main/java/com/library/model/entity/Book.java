package com.library.model.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
@Getter
@Setter
public class Book extends CustomEntity{

    @Column
    private String title;

    @Column
    private Short publicationYear;

    @Column
    private Integer stack;

    @Column
    private String unit;

    @JoinColumn(name = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Publisher publisher;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
                                     inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
}
