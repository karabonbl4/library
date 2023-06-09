package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherDto extends ParentDto {

    private String title;

    private String fullAddress;
}
