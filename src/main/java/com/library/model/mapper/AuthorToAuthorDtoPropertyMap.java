package com.library.model.mapper;

import com.library.model.dto.AuthorNameDto;
import com.library.model.entity.Author;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class AuthorToAuthorDtoPropertyMap<T extends AuthorNameDto> extends PropertyMap<Author, T> {

    private final Converter<Author, String> fullName = context -> context.getSource().getName() + " " + context.getSource().getSurname();

    @Override
    protected void configure() {
        using(fullName)
                .map(source, destination.getFullName());
    }
}
