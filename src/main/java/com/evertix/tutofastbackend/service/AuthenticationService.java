package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.security.payload.request.LoginRequest;
import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

}
