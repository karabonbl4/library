package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.BookStoredDto;
import com.library.dto.BookStoredTitleDto;

import java.util.List;

public interface BookStoredService {

    List<BookStoredTitleDto> findAllPageable(Integer page, Integer countOnPage);

    BookStoredDto findById(String id);

    BookStoredDto moveToStorage(Long bookId);

    BookDto recovery(String bookId);
}
