package com.evertix.tutofastbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
public class User extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 20,message = "Username name must be less than 20 characters ")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120,message = "Password must be less than 120 characters ")
    private String password;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid Email")
    @Size(max = 100)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //User Additional Data

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "LastName cannot be blank")
    @Size(max = 100)
    private String lastName;

    @Column(nullable = true)
    private LocalDate birthday;


    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 12)
    private String phone;


    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 150)
    private String address;

    private int totalStar;

    private Boolean active;

    @Size(max = 50)
    private String linkedln;

    /*
    @NotBlank(message = "Role cannot be blank")
    @Size(max = 20)
    private String role;
    */

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "assignations",
            joinColumns = {@JoinColumn(name = "teacher_id")}, inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courses;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
