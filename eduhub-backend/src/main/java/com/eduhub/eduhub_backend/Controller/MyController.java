package com.eduhub.eduhub_backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController{
    @GetMapping("/home")
 public String control(){
     return "My name is Deepak";
 }
}
