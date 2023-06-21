package com.library.service.impl;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.model.mapper.AuthorMapper;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> findAllWithPageable(Integer page, Integer sizeOnPage) {
        return authorRepository.findAll(PageRequest.of(page - 1, sizeOnPage)).stream()
                .map(authorMapper::mapToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(Long id) {
        return authorMapper.mapToAuthorDto(authorRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author author = authorRepository.save(authorMapper.mapToAuthor(authorDto));
        return authorMapper.mapToAuthorDto(author);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Author author = authorRepository.save(authorMapper.mapToAuthor(authorDto));
        return authorMapper.mapToAuthorDto(author);
    }

    @Override
    public void softDelete(Long authorId) {
        authorRepository.delete(authorId);
    }
}