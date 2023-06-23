package com.library.model.mapper;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class PublisherDtoToPublisherPropertyMap<T extends PublisherDto> extends PropertyMap<T, Publisher> {

    private final Converter<String, String> country = context -> context.getSource().split(", ")[0];

    private final Converter<String, String> city = context -> context.getSource().split(", ")[1];

    private final Converter<String, String> street = context -> context.getSource().split(", ")[2];

    private final Converter<String, String> buildingNumber = context -> context.getSource().split(", ")[3];

    @Override
    protected void configure() {
        using(country)
                .map(source.getFullAddress(), destination.getCountry());
        using(city)
                .map(source.getFullAddress(), destination.getCity());
        using(street)
                .map(source.getFullAddress(), destination.getStreet());
        using(buildingNumber)
                .map(source.getFullAddress(), destination.getBuildingNumber());
    }
}
