package com.library.service;

import com.library.model.dto.PublisherDto;

import java.util.List;

public interface PublisherService {

    List<PublisherDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    PublisherDto findById(Long id);

    PublisherDto saveOrUpdate(PublisherDto publisherDto);

    void deleteById(Long id);

    PublisherDto softDelete(PublisherDto publisherDto);

}
