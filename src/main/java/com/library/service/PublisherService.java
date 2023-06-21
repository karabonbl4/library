package com.library.service;

import com.library.model.dto.PublisherDto;

import java.util.List;

public interface PublisherService {

    List<PublisherDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    PublisherDto findById(Long id);

    PublisherDto save(PublisherDto publisherDto);

    PublisherDto update(PublisherDto publisherDto);

    void softDelete(Long publisherId);
}