package com.library.service;


import com.library.dto.AuthorDto;
import com.library.dto.AuthorNameDto;

import java.util.List;


public interface AuthorService {

    List<AuthorNameDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    AuthorDto findById(Long id);

    AuthorDto saveOrUpdate(AuthorDto authorDto);

    void softDelete(Long authorId);
}
