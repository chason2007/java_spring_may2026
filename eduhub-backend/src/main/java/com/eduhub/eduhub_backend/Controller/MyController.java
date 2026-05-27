package com.eduhub.eduhub_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/home")
    public String home() {
        return "Welcome to the EduHub backend!";
    }
}