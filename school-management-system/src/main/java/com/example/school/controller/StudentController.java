package com.example.school.controller;

import com.example.school.dao.BookDao;
import com.example.school.dao.StudentDao;
import com.example.school.dto.StudentRequestDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Book;
import com.example.school.model.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student API", description = "Управление учениками школы (Student / Person)")
public class StudentController {

    private final StudentDao studentDao;
    private final BookDao bookDao;

    public StudentController(StudentDao studentDao, BookDao bookDao) {
        this.studentDao = studentDao;
        this.bookDao = bookDao;
    }

    @GetMapping
    @Operation(summary = "Получить всех учеников")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentDao.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ученика по ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Студент с ID " + id + " не найден"));
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Получить учебники, закрепленные за учеником")
    public ResponseEntity<List<Book>> getStudentBooks(@PathVariable Long id) {
        studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Студент с ID " + id + " не найден"));
        return ResponseEntity.ok(bookDao.findByStudentId(id));
    }

    @PostMapping
    @Operation(summary = "Зарегистрировать нового ученика")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequestDto dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setGrade(dto.getGrade());
        student.setRoomId(dto.getRoomId());

        Student saved = studentDao.save(student);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные ученика")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDto dto) {
        Student existing = studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Студент с ID " + id + " не найден"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setGrade(dto.getGrade());
        existing.setRoomId(dto.getRoomId());

        studentDao.update(id, existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ученика")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentDao.deleteById(id)) {
            throw new ResourceNotFoundException("Студент с ID " + id + " не найден");
        }
        return ResponseEntity.noContent().build();
    }
}
