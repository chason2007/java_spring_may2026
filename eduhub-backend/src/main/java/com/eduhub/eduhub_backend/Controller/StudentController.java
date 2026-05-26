package com.eduhub.eduhub_backend.Controller;


import com.eduhub.eduhub_backend.component.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @GetMapping("student")
    public Student getStudent(){

    }



}
