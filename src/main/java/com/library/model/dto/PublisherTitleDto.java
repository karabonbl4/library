package com.library.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PublisherTitleDto extends ParentDto {

    private String title;

    @Override
    public String toString() {
        return "PublisherTitleDto{" +
                "title='" + title + '\'' +
                '}';
    }
}
