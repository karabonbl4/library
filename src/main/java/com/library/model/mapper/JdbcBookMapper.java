package com.library.model.mapper;

import com.library.model.entity.Book;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcBookMapper implements JdbcMapper<Book> {

    @Override
    public Book mapToEntity(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setPublicationYear(rs.getShort("publication_year"));
            book.setStack(rs.getInt("stack"));
            book.setUnit(rs.getString("unit"));
            book.setTaken(rs.getBoolean("taken"));
            book.setInventoryNumber(rs.getString("inventory_number"));
            book.setDeleted(rs.getBoolean("deleted"));
            return book;
        }
        throw new SQLException("Row with required parameters not found!");
    }
}
