package com.library.model.mapper;

import com.library.model.dto.BookDto;
import com.library.model.entity.BookStored;
import com.library.model.dto.BookStoredDto;
import com.library.model.dto.BookStoredTitleDto;
import com.library.model.entity.Book;
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
        modelMapper.createTypeMap(Book.class, BookStored.class)
                .addMappings(mapping -> mapping.skip(BookStored::setId))
                .<Integer>addMapping(Book::getStack, ((destination, value) -> destination.getBookshelf().setStack(value)))
                .<String>addMapping(Book::getUnit, (destination, value) -> destination.getBookshelf().setUnit(value));
        modelMapper.createTypeMap(BookStored.class, Book.class)
                .addMappings(mapping -> mapping.skip(Book::setId))
                .addMapping(src-> src.getBookshelf().getStack(), Book::setStack)
                .addMapping(src-> src.getBookshelf().getUnit(), Book::setUnit);
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

    public BookStored mapToBookStored(Book book){
        return modelMapper.map(book, BookStored.class);
    }

    public BookStoredDto mapToBookStoredDto(BookStored bookStored){
        return modelMapper.map(bookStored, BookStoredDto.class);
    }

    public BookStoredTitleDto mapToBookStoredTitleDto(BookStored bookStored){
        return modelMapper.map(bookStored, BookStoredTitleDto.class);
    }

    public Book mapBookStoredToBook(BookStored bookStored){
        return modelMapper.map(bookStored, Book.class);
    }
}
