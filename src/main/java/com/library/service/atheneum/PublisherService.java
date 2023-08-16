package com.library.service.atheneum;

import com.library.model.dto.PublisherDto;
import com.library.model.dto.PublisherTitleDto;

import java.util.List;

public interface PublisherService {

    List<PublisherTitleDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    PublisherDto findById(Long id);

    PublisherDto saveOrUpdate(PublisherDto publisherDto);

    void softDelete(Long publisherId);
}