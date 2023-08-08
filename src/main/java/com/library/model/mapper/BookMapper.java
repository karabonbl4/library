package com.library.model.mapper;

import com.library.model.dto.BookDto;
import com.library.model.entity.postgres.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Book converter based on ModelMapper to map Book in BookDto and vice versa
 */
@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper modelMapper;

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
}
