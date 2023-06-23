package com.library.model.mapper;

import com.library.model.dto.AuthorDto;
import com.library.model.dto.AuthorNameDto;
import com.library.model.entity.Author;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Author.class, AuthorDto.class)
                .addMappings(new AuthorToAuthorDtoPropertyMap<>());

        modelMapper.createTypeMap(Author.class, AuthorNameDto.class)
                .addMappings(new AuthorToAuthorDtoPropertyMap<>());

        modelMapper.createTypeMap(AuthorDto.class, Author.class)
                .addMappings(new AuthorDtoToAuthorPropertyMap<>());
    }

    public Author mapToAuthor(AuthorDto authorDto){
        return modelMapper.map(authorDto, Author.class);
    }

    public AuthorDto mapToAuthorDto(Author author){
        return modelMapper.map(author, AuthorDto.class);
    }
}
