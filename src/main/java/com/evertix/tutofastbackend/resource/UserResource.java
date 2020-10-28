package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.Course;
import com.evertix.tutofastbackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResource {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private String name;
    private String lastName;
    private String dni;
    private String phone;
    private LocalDate birthday;
    private String address;
    private Short creditHours;
    private BigDecimal averageStars;
    private Boolean active;
    private String linkedin;
    private List<Course> courses;
}
