package com.library.model.mapper;

import com.library.common.TestStorage;
import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    private TestStorage testStorage;

    @BeforeEach
    public void initData(){
        testStorage = new TestStorage();
    }

    @Test
    void mapToBookDto() {
        BookDto bookDto = bookMapper.mapToBookDto(testStorage.getTestBook());

        assertEquals(bookDto.getAuthorsFullName().size(), testStorage.getTestBook().getAuthors().size());
        assertEquals(bookDto.getAuthorsFullName(), testStorage.getTestBook().getAuthors().stream().map(author -> author.getName().concat(" ").concat(author.getSurname())).collect(Collectors.toList()));
        assertEquals(bookDto.getTitle(), testStorage.getTestBook().getTitle());
        assertEquals(bookDto.getStack(), testStorage.getTestBook().getStack());
        assertEquals(bookDto.getUnit(), testStorage.getTestBook().getUnit());
    }

    @Test
    void mapToBook() {
        Book book = bookMapper.mapToBook(testStorage.getBookDto());

        assertEquals(book.getTitle(), testStorage.getBookDto().getTitle());
        assertEquals(book.getStack(), testStorage.getBookDto().getStack());
        assertEquals(book.getUnit(), testStorage.getBookDto().getUnit());
    }
}