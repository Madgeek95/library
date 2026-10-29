package com.example.school.dto;

import jakarta.validation.constraints.NotBlank;

public class BookRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Subject is required")
    private String subject;

    private Long studentId;

    public BookRequestDto() {}

    public BookRequestDto(String title, String subject, Long studentId) {
        this.title = title;
        this.subject = subject;
        this.studentId = studentId;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
}
