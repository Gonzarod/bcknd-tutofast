package com.evertix.tutofastbackend.security.payload.request;

import com.evertix.tutofastbackend.model.ERole;
import com.evertix.tutofastbackend.model.Role;
import com.evertix.tutofastbackend.model.User;
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


    public SignUpRequest(String username, String password, String email, Set<String> role, String name,
                         String lastName,String dni, String phone,LocalDate birthday, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
    }




}