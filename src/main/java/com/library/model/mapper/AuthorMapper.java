package com.library.model.mapper;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    private final ModelMapper modelMapper;

    public Author mapToAuthor(AuthorDto authorDto){
        String[] fullNameArray = authorDto.getFullName().split(" ");
        Author mappedAuthor = modelMapper.map(authorDto, Author.class);
        mappedAuthor.setName(fullNameArray[0]);
        String surname = null;
        for (int i = 1; i < fullNameArray.length; i++) {
            surname = fullNameArray[i];
            if (i != fullNameArray.length - 1){
                surname = surname.concat(" ");
            }
        }
        mappedAuthor.setSurname(surname);

        return mappedAuthor;
    }

    public AuthorDto mapToAuthorDto(Author author){
        return modelMapper.map(author, AuthorDto.class);
    }
}
