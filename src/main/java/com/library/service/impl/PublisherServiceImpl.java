package com.library.service.impl;

import com.library.exception.MissingRequiredDataException;
import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import com.library.model.mapper.PublisherMapper;
import com.library.repository.PublisherRepository;
import com.library.service.PublisherService;
import com.library.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService, ValidateService<Publisher> {

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
        this.validateId(id);
        return publisherMapper.mapToPublisherDto(publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public PublisherDto save(PublisherDto publisherDto) {
        Publisher publisher = publisherMapper.mapToPublisher(publisherDto);
        this.validateEntity(publisher);
        return publisherMapper.mapToPublisherDto(publisherRepository.save(publisher));
    }

    @Override
    public PublisherDto update(PublisherDto publisherDto) {
        this.validateId(publisherDto.getId());
        Publisher publisher = publisherMapper.mapToPublisher(publisherDto);
        this.validateEntity(publisher);
        return publisherMapper.mapToPublisherDto(publisherRepository.save(publisher));
    }

    @Override
    public void softDelete(Long publisherId) {
        this.validateId(publisherId);
        publisherRepository.delete(publisherId);
    }

    @Override
    public void validateEntity(Publisher publisher) {
        List<String> missingType = new ArrayList<>();
        if (publisher.getTitle() == null){
            missingType.add("title");
        }
        if (publisher.getCountry() == null) {
            missingType.add("country");
        }
        if (publisher.getCity() == null) {
            missingType.add("city");
        }
        if (publisher.getStreet() == null) {
            missingType.add("street");
        }
        if (publisher.getBuildNumber() == null) {
            missingType.add("build number");
        }
        if (missingType.size() > 0){
            throw new MissingRequiredDataException(missingType);
        }
    }
}
