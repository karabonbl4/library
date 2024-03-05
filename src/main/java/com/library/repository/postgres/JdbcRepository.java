package com.library.repository.postgres;

import java.sql.SQLException;

public interface JdbcRepository<T, ID> {

    T getById(ID id) throws SQLException;
}
