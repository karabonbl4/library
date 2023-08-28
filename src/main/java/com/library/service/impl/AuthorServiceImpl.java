package com.library.service.impl;

import com.library.model.dto.AuthorDto;
import com.library.model.dto.AuthorNameDto;
import com.library.model.entity.Author;
import com.library.model.mapper.AuthorMapper;
import com.library.repository.postgres.AuthorRepository;
import com.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.library.constant.ApplicationConstant.AUTHOR_CANT_BE_DELETED;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorNameDto> findAllWithPageable(Integer page, Integer sizeOnPage) {
        return authorRepository.findAll(PageRequest.of(page - 1, sizeOnPage, Sort.by("id").ascending())).stream()
                .map(authorMapper::mapToAuthorNameDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(Long id) {
        return authorMapper.mapToAuthorDto(authorRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AuthorDto saveOrUpdate(AuthorDto authorDto) {
        Author author = authorRepository.save(authorMapper.mapToAuthor(authorDto));
        return authorMapper.mapToAuthorDto(author);
    }

    @Override
    public void softDelete(Long authorId) {
        if (authorRepository.countBookByAuthor(authorId) > 0) {
            throw new DataIntegrityViolationException(AUTHOR_CANT_BE_DELETED);
        }
        authorRepository.delete(authorId);
    }
}