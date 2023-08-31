package com.library.repository.mongo;

import com.library.model.entity.BookStored;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookStoredRepository extends MongoRepository<BookStored, ObjectId> {

    List<BookStored> findByAuthors_Id(Long authorId);
}
