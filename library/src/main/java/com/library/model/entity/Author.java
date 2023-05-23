package com.library.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author extends CustomEntity {

    @Column
    private String name;

    @Column
    private String surname;

    @Transient
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate birthDay;

    @Column(name = "date_of_death")
    private LocalDate ripDay;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;

    @PostLoad
    private void setFullName(){
        this.fullName = name.concat(" ").concat(surname);
    }

}
