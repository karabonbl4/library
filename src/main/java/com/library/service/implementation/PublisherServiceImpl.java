package com.library.service.implementation;

import com.library.model.dto.PublisherDto;
import com.library.model.entity.Publisher;
import com.library.repository.PublisherRepository;
import com.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    private final ModelMapper modelMapper;


    @Override
    public PublisherDto findById(Long id) {
        return modelMapper.map(publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new), PublisherDto.class);
    }

    @Override
    public PublisherDto saveOrUpdate(PublisherDto publisherDto) {
        Publisher savedPublisher = modelMapper.map(publisherDto, Publisher.class);
        return modelMapper.map(publisherRepository.save(savedPublisher), PublisherDto.class);
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
