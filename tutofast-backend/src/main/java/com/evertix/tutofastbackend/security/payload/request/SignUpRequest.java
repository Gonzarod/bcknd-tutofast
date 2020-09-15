package com.evertix.tutofastbackend.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 5, max = 30)
    private String username;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 10, min = 8)
    private String dni;

    @NotBlank
    @Size(max = 12, min = 9)
    private String phone;

    @NotBlank
    private LocalDate birthday;

    @NotBlank
    @Size(max = 150)
    private String address;
}