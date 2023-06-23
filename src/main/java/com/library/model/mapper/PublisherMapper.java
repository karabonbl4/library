package com.library.model.mapper;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    private final ModelMapper modelMapper;

    public PublisherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Publisher.class, PublisherDto.class)
                .addMappings(new PublisherToPublisherDtoPropertyMap<>());

        modelMapper.createTypeMap(PublisherDto.class, Publisher.class)
                .addMappings(new PublisherDtoToPublisherPropertyMap<>());
    }

    public PublisherDto mapToPublisherDto(Publisher publisher){
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public Publisher mapToPublisher(PublisherDto publisherDto){
        return modelMapper.map(publisherDto, Publisher.class);
    }
}
