package com.library.repository.mongo;

import com.library.model.entity.mongo.StoredBook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepositoryMongo extends MongoRepository<StoredBook, ObjectId> {

}
