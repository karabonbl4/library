package com.library.model.mapper;

import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookDto mapToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book mapToBook(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
