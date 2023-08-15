package com.library.repository.mongo;

import com.library.model.document.BookStored;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookStoredRepository extends MongoRepository<BookStored, ObjectId> {

}
