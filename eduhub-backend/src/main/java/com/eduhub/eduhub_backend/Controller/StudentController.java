package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @GetMapping("/student")
    public Student getStudent() {
        return new Student(1, "John Doe", "john.doe@example.com");
    }
}