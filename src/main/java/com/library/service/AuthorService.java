package com.library.service;


import com.library.model.dto.AuthorDto;


public interface AuthorService {

    AuthorDto findById(Long id);

    AuthorDto saveOrUpdate(AuthorDto authorDto);

    void deleteById(Long id);

    AuthorDto softDelete(AuthorDto authorDto);
}
