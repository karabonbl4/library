package com.library.service;

import com.library.model.dto.BookDto;
import com.library.model.dto.BookStoredDto;
import com.library.model.dto.BookStoredTitleDto;

import java.util.List;

public interface BookStoredService {

    List<BookStoredTitleDto> findAllPageable(Integer page, Integer countOnPage);

    BookStoredDto findById(String id);

    BookStoredDto moveToStorage(Long bookId);

    BookDto recovery(String bookId);
}
