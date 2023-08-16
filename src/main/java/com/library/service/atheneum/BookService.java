package com.library.service.atheneum;

import com.library.model.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    BookDto findById(Long id);

    BookDto save(BookDto bookDto);
    BookDto update(BookDto bookDto);

    void softDelete(Long bookId);

    void deleteById(Long bookId);
}