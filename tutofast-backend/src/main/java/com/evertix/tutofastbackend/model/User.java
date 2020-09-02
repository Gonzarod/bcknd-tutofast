package com.evertix.tutofastbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100)
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100)
    private String password;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100)
    private String name;

    @NotNull(message = "LastName cannot be null")
    @NotBlank(message = "LastName cannot be blank")
    @Size(max = 100)
    private String lastName;

    @Column(nullable = false, updatable = false)
    private LocalDate birthday;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 30)
    private String email;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 12)
    private String phone;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 150)
    private String address;

    private int totalStar;

    private Boolean active;

    @Size(max = 50)
    private String linkedln;

    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be blank")
    @Size(max = 20)
    private String role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "assignations",
               joinColumns = {@JoinColumn(name = "teacher_id")}, inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courses;
}
