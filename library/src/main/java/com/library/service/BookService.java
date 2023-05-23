package com.library.service;

import com.library.model.dto.BookDto;

public interface BookService {

    BookDto findById(Long id);

    BookDto saveOrUpdate(BookDto bookDto);

    void deleteById(Long id);

    BookDto softDelete(BookDto bookDto);

}
