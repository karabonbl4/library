package com.library.repository.postgres.impl;

import com.library.model.entity.Book;
import com.library.model.mapper.JdbcMapper;
import com.library.repository.postgres.JdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepositoryImpl implements JdbcRepository<Book, Long> {

    private final DataSourceProperties postgresDataSourceProperties;

    private final JdbcMapper<Book> jdbcBookMapper;

    @Override
    public Book getById(Long aLong) throws SQLException {
        Connection connection = DriverManager.getConnection(postgresDataSourceProperties.getUrl(),
                postgresDataSourceProperties.getUsername(),
                postgresDataSourceProperties.getPassword());
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
        statement.setLong(1, aLong);
        ResultSet resultSet = statement.executeQuery();

        connection.close();

        return jdbcBookMapper.mapToEntity(resultSet);
    }
}
