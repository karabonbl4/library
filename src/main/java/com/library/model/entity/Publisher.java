package com.library.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publisher extends ParentEntity {

    @Column
    private String title;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String street;

    @Column(name = "build_num")
    private String buildNumber;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
