package com.library.repository.mongo;

import com.library.model.entity.BookStored;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookStoredRepository extends MongoRepository<BookStored, ObjectId> {

}