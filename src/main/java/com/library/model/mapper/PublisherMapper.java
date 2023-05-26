package com.library.model.mapper;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherMapper {

    private final ModelMapper modelMapper;

    public PublisherDto mapToPublisherDto(Publisher publisher){
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public Publisher mapToPublisher(PublisherDto publisherDto){
        String[] splitFullAddress = publisherDto.getFullAddress().split(", ");

        return Publisher.builder()
                .title(publisherDto.getTitle())
                .country(splitFullAddress[0])
                .city(splitFullAddress[1])
                .street(splitFullAddress[2])
                .buildNumber(splitFullAddress[3])
                .build();
    }
}
