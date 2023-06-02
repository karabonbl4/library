package com.library.service.impl;

import com.library.model.dto.PublisherDto;
import com.library.model.mapper.PublisherMapper;
import com.library.repository.PublisherRepository;
import com.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;


    @Override
    public List<PublisherDto> findAllWithPageable(Integer page, Integer sizeOnPage) {
        Pageable paging = PageRequest.of(page - 1, sizeOnPage);
        return publisherRepository.findAll(paging).stream()
                .map(publisherMapper::mapToPublisherDto)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDto findById(Long id) {
        return publisherMapper.mapToPublisherDto(publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public PublisherDto saveOrUpdate(PublisherDto publisherDto) {
        return publisherMapper.mapToPublisherDto(publisherRepository.save(publisherMapper.mapToPublisher(publisherDto)));
    }

    @Override
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public PublisherDto softDelete(PublisherDto publisherDto) {
        publisherDto.setDeleted(true);
        return this.saveOrUpdate(publisherDto);
    }
}
