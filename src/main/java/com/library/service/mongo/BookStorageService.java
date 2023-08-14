package com.library.service.mongo;

import com.library.model.dto.BookDto;
import com.library.model.entity.mongo.StoredBook;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookStorageService {

    List<StoredBook> findAllPageable(Integer page, Integer countOnPage);

    StoredBook findById(ObjectId id);

    StoredBook moveToStorage(Long bookId);

    BookDto recovery(ObjectId bookId);
}
