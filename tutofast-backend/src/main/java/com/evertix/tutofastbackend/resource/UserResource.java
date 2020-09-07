package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserResource {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private String address;
    private int totalStar;
    private Boolean active;
    private String linkedln;
    private Set<Role> roles;
    //private String role;
}
