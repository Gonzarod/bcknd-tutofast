package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.security.payload.request.LoginRequest;
import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;

import com.evertix.tutofastbackend.service.AuthenticationService;
import com.evertix.tutofastbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "User Registration", description = "Registration for both teacher and student user", tags = {"Authentication"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return this.authenticationService.registerUser(signUpRequest);
    }

    @PostMapping("/signin")
    @Operation(summary = "User Log in", description = "Log in for teacher, student and admin user. Returns JWT and user info", tags = {"Authentication"})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return this.authenticationService.authenticateUser(loginRequest);
    }

    @GetMapping("/username/{username}/role")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get Role by Username. Endpoint is public", description = "Get Role by Username. Endpoint is public",
            tags = {"User"})
    //,security = @SecurityRequirement(name = "bearerAuth")
    public String getAllUsersStudents(@PathVariable String username){
        return this.userService.getRoleByUsername(username);
    }



}
