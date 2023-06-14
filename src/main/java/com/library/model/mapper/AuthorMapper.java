package com.library.model.mapper;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.model.entity.Book;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;


@Component
public class AuthorMapper {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Author.class, AuthorDto.class)
                .addMappings(mapping -> mapping.when(Conditions.isNull()).skip(Author::getBooks, AuthorDto::setBooksTitle))
                .addMappings(new PropertyMap<Author, AuthorDto>() {
                    @Override
                    protected void configure() {
                        using(context -> generatedFullName(
                                ((Author)context.getSource()).getName(),
                                ((Author)context.getSource()).getSurname()))
                                .map(source, destination.getFullName());
                        using(context ->
                                ((Author)context.getSource()).getBooks()
                                        .stream()
                                        .map(Book::getTitle)
                                        .collect(Collectors.toList()))
                                .map(source, destination.getBooksTitle());
                    }
                });

        modelMapper.createTypeMap(AuthorDto.class, Author.class)
                .addMappings(new PropertyMap<AuthorDto, Author>() {
                    @Override
                    protected void configure() {
                        using(context -> getNameOrSurnameFromFullName(
                                ((AuthorDto)context.getSource()).getFullName(),
                                0))
                                .map(source, destination.getName());
                        using(context -> getNameOrSurnameFromFullName(
                                ((AuthorDto)context.getSource()).getFullName(),
                                1))
                                .map(source, destination.getSurname());
                    }
                });
    }

    public Author mapToAuthor(AuthorDto authorDto){
        return modelMapper.map(authorDto, Author.class);
    }

    public AuthorDto mapToAuthorDto(Author author){
        return modelMapper.map(author, AuthorDto.class);
    }

    public String generatedFullName(String name, String surname){
        return name.concat(" ").concat(surname);
    }

    String getNameOrSurnameFromFullName(String fullname, int element){
        return Arrays.stream(fullname.split(" ")).toList().get(element);
    }
}
