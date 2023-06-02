package com.library.model.dto;

import com.library.model.mapper.shortDto.BookTitleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto extends ParentDto {

    private String fullName;

    private LocalDate birthDay;

    private LocalDate ripDay;

    private List<BookTitleDto> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorDto authorDto = (AuthorDto) o;

        if (!fullName.equals(authorDto.fullName)) return false;
        if (birthDay != null ? !birthDay.equals(authorDto.birthDay) : authorDto.birthDay != null) return false;
        return ripDay != null ? ripDay.equals(authorDto.ripDay) : authorDto.ripDay == null;
    }

    @Override
    public int hashCode() {
        int result = fullName.hashCode();
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (ripDay != null ? ripDay.hashCode() : 0);
        return result;
    }
}
