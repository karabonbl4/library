package com.library.service;

import com.library.model.dto.PublisherDto;

public interface PublisherService {

    PublisherDto findById(Long id);

    PublisherDto saveOrUpdate(PublisherDto publisherDto);

    void deleteById(Long id);

    PublisherDto softDelete(PublisherDto publisherDto);

}
