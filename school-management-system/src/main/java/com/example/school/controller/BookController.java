package com.example.school.controller;

import com.example.school.dao.BookDao;
import com.example.school.dto.BookRequestDto;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book / Textbook API", description = "Управление школьной библиотекой и учебниками (Book / Darslik / Daftar / Kutubxona)")
public class BookController {

    private final BookDao bookDao;

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    @Operation(summary = "Получить список всех учебников")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookDao.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить учебник по ID")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Учебник с ID " + id + " не найден"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @Operation(summary = "Добавить новый учебник в библиотеку")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setSubject(dto.getSubject());
        book.setStudentId(dto.getStudentId());

        Book saved = bookDao.save(book);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/issue/{studentId}")
    @Operation(summary = "Выдать учебник ученику")
    public ResponseEntity<String> issueBook(@PathVariable Long bookId, @PathVariable Long studentId) {
        if (!bookDao.issueToStudent(bookId, studentId)) {
            throw new ResourceNotFoundException("Не удалось выдать учебник с ID " + bookId);
        }
        return ResponseEntity.ok("Учебник успешно выдан ученику " + studentId);
    }

    @PostMapping("/{bookId}/return")
    @Operation(summary = "Вернуть учебник в библиотеку")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        if (!bookDao.returnFromStudent(bookId)) {
            throw new ResourceNotFoundException("Не удалось вернуть учебник с ID " + bookId);
        }
        return ResponseEntity.ok("Учебник успешно возвращен в библиотеку");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Списать/удалить учебник")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookDao.deleteById(id)) {
            throw new ResourceNotFoundException("Учебник с ID " + id + " не найден");
        }
        return ResponseEntity.noContent().build();
    }
}
