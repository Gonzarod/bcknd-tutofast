package com.evertix.tutofastbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

/*
    @GetMapping("/student")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }
    */

    @GetMapping("/all")
    public String anybody() {
        return "Public Content.";
    }

    @GetMapping("/autenticado")
    @PreAuthorize("isAuthenticated()")
    public String getResourceForAutenticado() {
        return "Content for all autenticado.";
    }

    @GetMapping("/studentandteacher")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public String allAccess() {
        return "Content only for Student and Teacher";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminResource() {
        return "ONLY FOR ADMIN";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentResource() {
        return "ONLY FOR STUDENT";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String teacherResource() {
        return "ONLY FOR TEACHER";
    }


}
