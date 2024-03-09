package com.library.repository.postgres.impl;

import com.library.model.entity.Book;
import com.library.repository.postgres.JdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateBookRepositoryImpl implements JdbcTemplateRepository<Book> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Book save(Book book) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(prepareParams(book));
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO book (title, publication_year, stack, unit, publisher_id, inventory_number) " +
                        "values (:title, :publication_year, :stack, :unit, :publisher_id, :inventory_number)",
                sqlParameterSource, keyHolder, new String[]{"id"});
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    private Map<String, Object> prepareParams(Book book) {
        Map<String, Object> params = new HashMap<>();
        this.putIfPresent(params, book);
        return params;
    }

    private void putIfPresent(Map<String, Object> params, Book book){
        if (book.getTitle() != null) params.put("title", book.getTitle());
        if (book.getPublicationYear() != null) params.put("publication_year", book.getPublicationYear());
        if (book.getStack() != null) params.put("stack", book.getStack());
        if (book.getUnit() != null) params.put("unit", book.getUnit());
        if (book.getPublisher() != null) params.put("publisher_id", book.getPublisher().getId());
        if (book.getInventoryNumber() != null) params.put("inventory_number", book.getInventoryNumber());
    }
}
