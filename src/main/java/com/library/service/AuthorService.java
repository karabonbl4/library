package com.library.service;


import com.library.model.dto.AuthorDto;

import java.util.List;


public interface AuthorService {

    List<AuthorDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    AuthorDto findById(Long id);

    AuthorDto save(AuthorDto authorDto);

    AuthorDto update(AuthorDto authorDto);

    void softDelete(Long authorId);
}
