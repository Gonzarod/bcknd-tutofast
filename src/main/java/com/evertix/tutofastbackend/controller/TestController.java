package com.evertix.tutofastbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String anybody() {
        return "Public Content.";
    }

    @GetMapping("/autenticado")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "My endpoint FOR Autenticados",security = @SecurityRequirement(name = "bearerAuth"), tags = {"Test"})
    public String getResourceForAutenticado() {
        return "Content for all autenticado.";
    }

    @GetMapping("/studentandteacher")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_TEACHER')")
    @Operation(summary = "My endpoint FOR Student and Teacher",security = @SecurityRequirement(name = "bearerAuth"), tags = {"Test"})
    public String allAccess() {
        return "Content only for Student and Teacher";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "My endpoint FOR aDMINS",security = @SecurityRequirement(name = "bearerAuth"), tags = {"Test"})
    public String adminResource() {
        return "ONLY FOR ADMIN";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @Operation(summary = "My endpoint FOR  STUDENT",security = @SecurityRequirement(name = "bearerAuth"), tags = {"Test"})
    public String studentResource() {
        return "ONLY FOR STUDENT";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @Operation(summary = "My endpoint FOR TEACHER",security = @SecurityRequirement(name = "bearerAuth"), tags = {"Test"})
    public String teacherResource() {
        return "ONLY FOR TEACHER";
    }
}
