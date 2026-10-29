package com.example.school.model;

import java.time.LocalDateTime;

public class Book {
    private Long id;
    private String title;
    private String subject;
    private Long studentId;
    private LocalDateTime issuedAt;

    public Book() {}

    public Book(Long id, String title, String subject, Long studentId, LocalDateTime issuedAt) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.studentId = studentId;
        this.issuedAt = issuedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
}
