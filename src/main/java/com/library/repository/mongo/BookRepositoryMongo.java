package com.library.repository.mongo;

import com.library.model.entity.mongo.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepositoryMongo extends MongoRepository<Book, Long> {

}
