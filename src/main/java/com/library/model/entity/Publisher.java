package com.library.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Transient
    private String fullAddress;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

    @PostLoad
    private void setFullAddress(){
        this.fullAddress = country.concat(", ").concat(city + ", ").concat(street + ", ").concat(buildNumber);
    }

}
