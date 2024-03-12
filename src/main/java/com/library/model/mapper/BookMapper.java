package com.library.model.mapper;

import com.library.dto.BookDto;
import com.library.dto.BookTitleDto;
import com.library.model.entity.BookStored;
import com.library.dto.BookStoredDto;
import com.library.dto.BookStoredTitleDto;
import com.library.model.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Book converter based on ModelMapper to map Book in BookDto and vice versa
 */
@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.createTypeMap(Book.class, BookStored.class)
                .addMappings(mapping -> mapping.skip(BookStored::setId))
                .addMappings(mapping -> mapping.skip(BookStored::setReferenceBooks))
                .<Integer>addMapping(Book::getStack, ((destination, value) -> destination.getBookShelf().setStack(value)))
                .<String>addMapping(Book::getUnit, (destination, value) -> destination.getBookShelf().setUnit(value));
        modelMapper.createTypeMap(BookStored.class, Book.class)
                .addMappings(mapping -> mapping.skip(Book::setId))
                .addMappings(mapping -> mapping.skip(Book::setReferenceBooks))
                .addMapping(src -> src.getBookShelf().getStack(), Book::setStack)
                .addMapping(src -> src.getBookShelf().getUnit(), Book::setUnit);
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

    public BookStored mapToBookStored(Book book) {
        return modelMapper.map(book, BookStored.class);
    }

    public BookTitleDto mapToBookTitleDto(Book book) {
        return modelMapper.map(book, BookTitleDto.class);
    }

    public BookStoredDto mapToBookStoredDto(BookStored bookStored) {
        return modelMapper.map(bookStored, BookStoredDto.class);
    }

    public BookStoredTitleDto mapToBookStoredTitleDto(BookStored bookStored) {
        return modelMapper.map(bookStored, BookStoredTitleDto.class);
    }

    public Book mapBookStoredToBook(BookStored bookStored) {
        return modelMapper.map(bookStored, Book.class);
    }
}
