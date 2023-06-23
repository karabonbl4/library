package com.library.model.mapper;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class PublisherToPublisherDtoPropertyMap<T extends PublisherDto> extends PropertyMap<Publisher, T> {

    private final Converter<Publisher, String> fullAddress= context -> String.format("%s, %s, %s, %s",
            context.getSource().getCountry(),
            context.getSource().getCity(),
            context.getSource().getStreet(),
            context.getSource().getBuildingNumber());

    @Override
    protected void configure() {
        using(fullAddress)
                .map(source, destination.getFullAddress());
    }
}
