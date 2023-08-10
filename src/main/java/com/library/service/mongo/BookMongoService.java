package com.library.service.mongo;

import com.library.model.dto.BookDto;
import com.library.model.entity.mongo.Book;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookMongoService {

    List<Book> findAllPageable(Integer page, Integer countOnPage);

    Book findById(ObjectId id);

    Book moveToStorage(Long bookId);

    BookDto recovery(ObjectId bookId);
}
