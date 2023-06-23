package com.library.service;


import com.library.model.dto.AuthorDto;

import java.util.List;


public interface AuthorService {

    List<AuthorDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    AuthorDto findById(Long id);

    AuthorDto saveOrUpdate(AuthorDto authorDto);

    void softDelete(Long authorId);
}
