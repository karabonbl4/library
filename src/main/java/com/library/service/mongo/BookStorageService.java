package com.library.service.mongo;

import com.library.model.dto.BookDto;
import com.library.model.document.BookStored;
import com.library.model.dto.BookStoredDto;
import com.library.model.dto.BookStoredTitleDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookStorageService {

    List<BookStoredTitleDto> findAllPageable(Integer page, Integer countOnPage);

    BookStoredDto findById(String id);

    BookStoredDto moveToStorage(Long bookId);

    BookDto recovery(String bookId);
}
