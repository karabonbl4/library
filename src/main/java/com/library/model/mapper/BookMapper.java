package com.library.model.mapper;

import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper, AuthorMapper authorMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Book.class, BookDto.class)
                .addMappings(new PropertyMap<Book, BookDto>() {
                    @Override
                    protected void configure() {
                        using(context -> ((Book) context.getSource()).getAuthors().stream()
                                .map(author -> authorMapper.generatedFullName(author.getName(), author.getSurname())).collect(Collectors.toList()))
                                .map(source, destination.getAuthorsFullName());

                    }
                });

    }

    public BookDto mapToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book mapToBook(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
