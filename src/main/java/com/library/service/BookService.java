package com.library.service;

import com.library.model.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAllWithPagination(Integer page, Integer sizeOnPage);

    BookDto findById(Long id);

    BookDto saveOrUpdate(BookDto bookDto);

    void deleteById(Long id);

    BookDto softDelete(BookDto bookDto);

}
