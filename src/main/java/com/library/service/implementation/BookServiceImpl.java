package com.library.service.implementation;

import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<BookDto> findAllWithPagination(Integer page, Integer sizeOnPage) {
        Pageable paging = PageRequest.of(page - 1, sizeOnPage);
        return bookRepository.findAll(paging).stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        return modelMapper.map(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new), BookDto.class);
    }

    @Override
    public BookDto saveOrUpdate(BookDto bookDto) {
        Book savedBook = modelMapper.map(bookDto, Book.class);
        return modelMapper.map(bookRepository.save(savedBook), BookDto.class);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto softDelete(BookDto bookDto) {
        bookDto.setDeleted(true);
        return this.saveOrUpdate(bookDto);
    }
}
