package com.library.service.impl;

import com.library.model.dto.PublisherDto;
import com.library.model.dto.PublisherTitleDto;
import com.library.model.entity.Publisher;
import com.library.model.mapper.PublisherMapper;
import com.library.repository.postgres.PublisherRepository;
import com.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService{

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;

    @Override
    public List<PublisherTitleDto> findAllWithPageable(Integer page, Integer sizeOnPage) {
        Pageable paging = PageRequest.of(page - 1, sizeOnPage, Sort.by("id").ascending());
        return publisherRepository.findAll(paging).stream()
                .map(publisherMapper::mapToPublisherTitleDto)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDto findById(Long id) {
        return publisherMapper.mapToPublisherDto(publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public PublisherDto saveOrUpdate(PublisherDto publisherDto) {
        Publisher publisher = publisherMapper.mapToPublisher(publisherDto);
        return publisherMapper.mapToPublisherDto(publisherRepository.save(publisher));
    }

    @Override
    public void softDelete(Long publisherId) {
        publisherRepository.delete(publisherId);
    }
}