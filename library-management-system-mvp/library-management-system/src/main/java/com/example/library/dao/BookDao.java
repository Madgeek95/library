package com.example.library.dao;

import com.example.library.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> new Book(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("isbn"),
            rs.getBoolean("available"),
            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
    );

    public List<Book> findAll() {
        String sql = "SELECT id, title, author, isbn, available, created_at FROM books ORDER BY id ASC";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, author, isbn, available, created_at FROM books WHERE id = ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, id);
        return books.stream().findFirst();
    }

    public Book save(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, available, created_at) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setBoolean(4, book.getAvailable() != null ? book.getAvailable() : true);
            ps.setTimestamp(5, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            book.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        } else if (keyHolder.getKey() != null) {
            book.setId(keyHolder.getKey().longValue());
        }
        book.setAvailable(book.getAvailable() != null ? book.getAvailable() : true);
        book.setCreatedAt(now);
        return book;
    }

    public boolean update(Long id, Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, available = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getAvailable(), id);
        return updatedRows > 0;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }

    public List<Book> searchByTitle(String title) {
        String sql = "SELECT id, title, author, isbn, available, created_at FROM books WHERE LOWER(title) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + title + "%");
    }
}
