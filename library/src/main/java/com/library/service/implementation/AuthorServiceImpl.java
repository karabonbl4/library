package com.library.service.implementation;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;


    @Override
    public AuthorDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public AuthorDto saveOrUpdate(AuthorDto authorDto) {
        Author savedAuthor = modelMapper.map(authorDto, Author.class);
        return modelMapper.map(authorRepository.save(savedAuthor), AuthorDto.class);
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
