package com.library.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Publisher extends ParentEntity {

    @NotBlank
    @Column
    private String title;

    @NotBlank
    @Column
    private String country;

    @NotBlank
    @Column
    private String city;

    @NotBlank
    @Column
    private String street;

    @NotBlank
    @Column
    private String buildingNumber;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
