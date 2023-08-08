package com.library.model.entity.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Publisher extends ParentEntity {

    @NotNull
    @Column
    private String title;

    @NotNull
    @Column
    private String country;

    @NotNull
    @Column
    private String city;

    @NotNull
    @Column
    private String street;

    @NotNull
    @Column(name = "building_number")
    private String buildingNumber;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
