package com.library.model.mapper;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    private final ModelMapper modelMapper;

    private final Converter<Publisher, String> fullAddress= context -> String.format("%s, %s, %s, %s",
            context.getSource().getCountry(),
            context.getSource().getCity(),
            context.getSource().getStreet(),
            context.getSource().getBuildingNumber());

    private final Converter<String, String> country = context -> context.getSource().split(", ")[0];

    private final Converter<String, String> city = context -> context.getSource().split(", ")[1];

    private final Converter<String, String> street = context -> context.getSource().split(", ")[2];

    private final Converter<String, String> buildingNumber = context -> context.getSource().split(", ")[3];

    public PublisherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Publisher.class, PublisherDto.class)
                .addMappings(new PropertyMap<Publisher, PublisherDto>() {
                    @Override
                    protected void configure() {
                        using(fullAddress)
                                .map(source, destination.getFullAddress());
                    }
                });

        modelMapper.createTypeMap(PublisherDto.class, Publisher.class)
                .addMappings(new PropertyMap<PublisherDto, Publisher>() {
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
                });
    }

    public PublisherDto mapToPublisherDto(Publisher publisher){
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public Publisher mapToPublisher(PublisherDto publisherDto){
        return modelMapper.map(publisherDto, Publisher.class);
    }
}
