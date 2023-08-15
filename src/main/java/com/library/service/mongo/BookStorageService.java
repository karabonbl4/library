package com.library.service.mongo;

import com.library.model.dto.BookDto;
import com.library.model.document.BookStored;
import com.library.model.dto.BookStoredDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookStorageService {

    List<BookStoredDto> findAllPageable(Integer page, Integer countOnPage);

    BookStored findById(ObjectId id);

    BookStored moveToStorage(Long bookId);

    BookDto recovery(ObjectId bookId);
}
