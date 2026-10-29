package com.example.library.controller;

import com.example.library.dao.BookDao;
import com.example.library.dto.BookRequestDto;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book Management API", description = "CRUD Operations for Books in Library System using Spring JDBC")
public class BookController {

    private final BookDao bookDao;

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(bookDao.searchByTitle(title));
        }
        return ResponseEntity.ok(bookDao.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookDao.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @Operation(summary = "Add a new book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDto requestDto) {
        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setAvailable(true);

        Book savedBook = bookDao.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDto requestDto) {
        Book existingBook = bookDao.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        existingBook.setTitle(requestDto.getTitle());
        existingBook.setAuthor(requestDto.getAuthor());
        existingBook.setIsbn(requestDto.getIsbn());

        bookDao.update(id, existingBook);
        return ResponseEntity.ok(existingBook);
    }

    @PatchMapping("/{id}/toggle-availability")
    @Operation(summary = "Toggle book availability (borrow/return)")
    public ResponseEntity<Book> toggleAvailability(@PathVariable Long id) {
        Book book = bookDao.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setAvailable(!book.getAvailable());
        bookDao.update(id, book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by ID")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookDao.deleteById(id)) {
            throw new BookNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }
}
