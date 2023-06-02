package com.library.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PublisherDto extends ParentDto {

    private String title;

    private String fullAddress;

//    @Builder
//
//    public PublisherDto(Long id, Boolean deleted, String title, String fullAddress) {
//        super(id, deleted);
//        this.title = title;
//        this.fullAddress = fullAddress;
//    }
}
