package com.library.service;

import com.library.dto.PublisherDto;
import com.library.dto.PublisherTitleDto;

import java.util.List;

public interface PublisherService {

    List<PublisherTitleDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    PublisherDto findById(Long id);

    PublisherDto saveOrUpdate(PublisherDto publisherDto);

    void softDelete(Long publisherId);
}