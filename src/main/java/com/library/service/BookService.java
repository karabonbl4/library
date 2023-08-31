package com.library.service;

import com.library.model.dto.BookDto;
import com.library.model.dto.BookTitleDto;

import java.util.List;

public interface BookService {

    List<BookTitleDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    BookDto findById(Long id);

    BookDto save(BookDto bookDto);
    BookDto update(BookDto bookDto);

    void softDelete(Long bookId);
}