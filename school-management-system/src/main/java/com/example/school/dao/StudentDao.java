package com.example.school.dao;

import com.example.school.model.Student;
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
public class StudentDao {

    private final JdbcTemplate jdbcTemplate;

    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> new Student(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("grade"),
            rs.getObject("room_id") != null ? rs.getLong("room_id") : null,
            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
    );

    public List<Student> findAll() {
        String sql = "SELECT id, first_name, last_name, grade, room_id, created_at FROM students ORDER BY id ASC";
        return jdbcTemplate.query(sql, studentRowMapper);
    }

    public Optional<Student> findById(Long id) {
        String sql = "SELECT id, first_name, last_name, grade, room_id, created_at FROM students WHERE id = ?";
        List<Student> students = jdbcTemplate.query(sql, studentRowMapper, id);
        return students.stream().findFirst();
    }

    public Student save(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, grade, room_id, created_at) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setInt(3, student.getGrade());
            if (student.getRoomId() != null) {
                ps.setLong(4, student.getRoomId());
            } else {
                ps.setNull(4, java.sql.Types.BIGINT);
            }
            ps.setTimestamp(5, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            student.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        } else if (keyHolder.getKey() != null) {
            student.setId(keyHolder.getKey().longValue());
        }
        student.setCreatedAt(now);
        return student;
    }

    public boolean update(Long id, Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, grade = ?, room_id = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, student.getFirstName(), student.getLastName(), student.getGrade(), student.getRoomId(), id);
        return updated > 0;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
