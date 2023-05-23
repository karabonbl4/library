package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PublisherDto extends CustomDto{

    private String title;

    private String country;

    private String city;

    private String street;

    private String buildNumber;
}
