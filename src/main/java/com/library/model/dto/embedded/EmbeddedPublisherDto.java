package com.library.model.dto.embedded;

import com.library.model.dto.ParentDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmbeddedPublisherDto extends ParentDto {

    private String title;
}
