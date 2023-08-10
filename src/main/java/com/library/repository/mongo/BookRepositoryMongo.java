package com.library.repository.mongo;

import com.library.model.entity.mongo.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepositoryMongo extends MongoRepository<Book, ObjectId> {

}
