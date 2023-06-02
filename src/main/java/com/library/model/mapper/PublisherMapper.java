package com.library.model.mapper;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PublisherMapper {

    private final ModelMapper modelMapper;

    public PublisherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Publisher.class, PublisherDto.class)
                .addMappings(new PropertyMap<Publisher, PublisherDto>() {
                    @Override
                    protected void configure() {
                        using(context -> generateFullAddress(
                                ((Publisher) context.getSource()).getCountry(),
                                ((Publisher) context.getSource()).getCity(),
                                ((Publisher) context.getSource()).getStreet(),
                                ((Publisher) context.getSource()).getBuildNumber()
                        )).map(source, destination.getFullAddress());
                    }
                });

        modelMapper.createTypeMap(PublisherDto.class, Publisher.class)
                .addMappings(new PropertyMap<PublisherDto, Publisher>() {
                    @Override
                    protected void configure() {
                        using(context -> getItemFromFullAddress(
                                ((PublisherDto) context.getSource()).getFullAddress(),
                                0))
                                .map(source, destination.getCountry());
                        using(context -> getItemFromFullAddress(
                                ((PublisherDto) context.getSource()).getFullAddress(),
                                1))
                                .map(source, destination.getCity());
                        using(context -> getItemFromFullAddress(
                                ((PublisherDto) context.getSource()).getFullAddress(),
                                2))
                                .map(source, destination.getStreet());
                        using(context -> getItemFromFullAddress(
                                ((PublisherDto) context.getSource()).getFullAddress(),
                                3))
                                .map(source, destination.getBuildNumber());
                    }
                });
    }

    public PublisherDto mapToPublisherDto(Publisher publisher){
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public Publisher mapToPublisher(PublisherDto publisherDto){
        return modelMapper.map(publisherDto, Publisher.class);
    }

    private String generateFullAddress(String country, String city, String street, String buildNum){
        return String.format("%s, %s, %s, %s", country, city, street, buildNum);
    }

    private String getItemFromFullAddress(String fullAddress, int item){
        return Arrays.stream(fullAddress.split(", ")).toList().get(item);
    }
}
