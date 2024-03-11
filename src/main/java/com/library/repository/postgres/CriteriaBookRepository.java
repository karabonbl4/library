package com.library.repository.postgres;

import com.library.model.entity.Book;

import java.util.List;

public interface CriteriaBookRepository {

    List<Book> findByStackAndUnit(Integer stack, String unit);

    void softDeleteById(Long id);
}
