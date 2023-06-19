package com.library.service.impl;

import com.library.exception.MissingRequiredDataException;
import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.model.mapper.AuthorMapper;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import com.library.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService, ValidateService<Author> {

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
        this.validateId(id);
        return authorMapper.mapToAuthorDto(authorRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author author = authorRepository.save(authorMapper.mapToAuthor(authorDto));
        this.validateEntity(author);
        return authorMapper.mapToAuthorDto(author);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        this.validateId(authorDto.getId());
        Author author = authorRepository.save(authorMapper.mapToAuthor(authorDto));
        this.validateEntity(author);
        return authorMapper.mapToAuthorDto(author);
    }

    @Override
    public void softDelete(Long authorId) {
        this.validateId(authorId);
        authorRepository.delete(authorId);
    }

    @Override
    public void validateEntity(Author author) {
        List<String> missingType = new ArrayList<>();
        if (author.getName() == null){
            missingType.add("name");
        }
        if (author.getSurname() == null) {
            missingType.add("surname");
        }
        if (author.getBirthDay() == null) {
            missingType.add("birthday");
        }
        if (missingType.size() > 0){
            throw new MissingRequiredDataException(missingType);
        }
    }
}
