package com.library.model.mapper;

import com.library.model.dto.AuthorDto;
import com.library.model.dto.AuthorNameDto;
import com.library.model.entity.Author;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    private final ModelMapper modelMapper;

    private final Converter<Author, String> fullName = context -> context.getSource().getName() + " " + context.getSource().getSurname();

    private final Converter<String, String> name = context -> context.getSource().split(" ")[0];

    private final Converter<String, String> surName = context -> context.getSource().split(" ")[1];

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Author.class, AuthorDto.class)
                .addMappings(new PropertyMap<Author, AuthorDto>() {
                    @Override
                    protected void configure() {
                        using(fullName)
                                .map(source, destination.getFullName());
                    }
                });

        modelMapper.createTypeMap(Author.class, AuthorNameDto.class)
                .addMappings(new PropertyMap<Author, AuthorNameDto>() {
                    @Override
                    protected void configure() {
                        using(fullName)
                                .map(source, destination.getFullName());
                    }
                });

        modelMapper.createTypeMap(AuthorDto.class, Author.class)
                .addMappings(new PropertyMap<AuthorDto, Author>() {
                    @Override
                    protected void configure() {
                        using(name)
                                .map(source.getFullName(), destination.getName());
                        using(surName)
                                .map(source.getFullName(), destination.getSurname());
                    }
                });

        modelMapper.createTypeMap(AuthorNameDto.class, Author.class)
                .addMappings(new PropertyMap<AuthorNameDto, Author>() {
                    @Override
                    protected void configure() {
                        using(name)
                                .map(source.getFullName(), destination.getName());
                        using(surName)
                                .map(source.getFullName(), destination.getSurname());
                    }
                });
    }

    public Author mapToAuthor(AuthorDto authorDto){
        return modelMapper.map(authorDto, Author.class);
    }

    public AuthorDto mapToAuthorDto(Author author){
        return modelMapper.map(author, AuthorDto.class);
    }

    public AuthorNameDto mapToAuthorNameDto(Author author){
        return modelMapper.map(author, AuthorNameDto.class);
    }
}
