package com.example.school.dao;

import com.example.school.model.Book;
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
            rs.getString("subject"),
            rs.getObject("student_id") != null ? rs.getLong("student_id") : null,
            rs.getTimestamp("issued_at") != null ? rs.getTimestamp("issued_at").toLocalDateTime() : null
    );

    public List<Book> findAll() {
        String sql = "SELECT id, title, subject, student_id, issued_at FROM books ORDER BY id ASC";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, subject, student_id, issued_at FROM books WHERE id = ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, id);
        return books.stream().findFirst();
    }

    public List<Book> findByStudentId(Long studentId) {
        String sql = "SELECT id, title, subject, student_id, issued_at FROM books WHERE student_id = ?";
        return jdbcTemplate.query(sql, bookRowMapper, studentId);
    }

    public Book save(Book book) {
        String sql = "INSERT INTO books (title, subject, student_id, issued_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getSubject());
            if (book.getStudentId() != null) {
                ps.setLong(3, book.getStudentId());
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            }
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            book.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        } else if (keyHolder.getKey() != null) {
            book.setId(keyHolder.getKey().longValue());
        }
        return book;
    }

    public boolean issueToStudent(Long bookId, Long studentId) {
        String sql = "UPDATE books SET student_id = ?, issued_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, studentId, Timestamp.valueOf(LocalDateTime.now()), bookId) > 0;
    }

    public boolean returnFromStudent(Long bookId) {
        String sql = "UPDATE books SET student_id = NULL, issued_at = NULL WHERE id = ?";
        return jdbcTemplate.update(sql, bookId) > 0;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
