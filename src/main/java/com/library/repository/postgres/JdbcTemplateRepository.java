package com.library.repository.postgres;

public interface JdbcTemplateRepository <T> {

    T save(T t);
}
