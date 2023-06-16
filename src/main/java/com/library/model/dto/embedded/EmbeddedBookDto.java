package com.library.model.dto.embedded;

import com.library.model.dto.ParentDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmbeddedBookDto extends ParentDto {

    private String title;
}
