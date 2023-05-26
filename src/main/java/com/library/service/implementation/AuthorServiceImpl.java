package com.library.service.implementation;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.model.mapper.AuthorMapper;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page - 1, sizeOnPage);
        return authorRepository.findAll(pageable).stream()
                .map(authorMapper::mapToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return authorMapper.mapToAuthorDto(author);
    }

    @Override
    public AuthorDto saveOrUpdate(AuthorDto authorDto) {
        Author savedAuthor = authorMapper.mapToAuthor(authorDto);
        return authorMapper.mapToAuthorDto(authorRepository.save(savedAuthor));
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto softDelete(AuthorDto authorDto) {
        authorDto.setDeleted(true);
        return this.saveOrUpdate(authorDto);
    }
}
