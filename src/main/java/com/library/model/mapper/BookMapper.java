package com.library.model.mapper;

import com.library.model.dto.BookDto;
import com.library.model.entity.mongo.StoredBook;
import com.library.model.entity.postgres.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Book converter based on ModelMapper to map Book in BookDto and vice versa
 */
@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        modelMapper.createTypeMap(BookDto.class, StoredBook.class)
                .addMappings(mapping -> mapping.skip(StoredBook::setId))
                .<Integer>addMapping(BookDto::getStack, ((destination, value) -> destination.getBookshelf().setStack(value)))
                .<String>addMapping(BookDto::getUnit, (destination, value) -> destination.getBookshelf().setUnit(value));
        modelMapper.createTypeMap(StoredBook.class, BookDto.class)
                .addMappings(mapping -> mapping.skip(BookDto::setId))
                .addMapping(src-> src.getBookshelf().getStack(), BookDto::setStack)
                .addMapping(src-> src.getBookshelf().getUnit(), BookDto::setUnit);
    }

    /**
     * @param book an instance of Book class
     * @return an instance of BookDto class
     */
    public BookDto mapToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    /**
     * @param bookDto an instance of BookDto class
     * @return an instance of Book class
     */
    public Book mapToBook(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    public StoredBook mapToMongoBook(BookDto bookDto){
        return modelMapper.map(bookDto, StoredBook.class);
    }

    public BookDto mapFromMongoBook(StoredBook storedBook){
        return modelMapper.map(storedBook, BookDto.class);
    }
}
