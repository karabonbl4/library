package com.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class PublisherTitleDto extends ParentDto {

    private String title;
}
