package com.library.model.mapper;

import com.library.model.dto.AuthorNameDto;
import com.library.model.entity.Author;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class AuthorDtoToAuthorPropertyMap<T extends AuthorNameDto> extends PropertyMap<T, Author> {

    private final Converter<String, String> name = context -> context.getSource().split(" ")[0];
    private final Converter<String, String> surName = context -> context.getSource().split(" ")[1];

    @Override
    protected void configure() {
        using(name)
                .map(source.getFullName(), destination.getName());
        using(surName)
                .map(source.getFullName(), destination.getSurname());
    }
}
