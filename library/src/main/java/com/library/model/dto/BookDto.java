package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto extends CustomDto{

    private String title;

    private Short publicationYear;

    private Integer stack;

    private String unit;

}
