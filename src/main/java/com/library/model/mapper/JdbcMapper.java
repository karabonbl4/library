package com.library.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcMapper<T> {

    T mapToEntity(ResultSet rs) throws SQLException;

}
