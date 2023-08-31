package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BookEmbeddedDto extends ParentDto {

    private String title;
}
