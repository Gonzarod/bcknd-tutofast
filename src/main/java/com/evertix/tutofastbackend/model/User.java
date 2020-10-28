package com.evertix.tutofastbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
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
@NoArgsConstructor
@Getter
@Setter
public class User extends AuditModel{

    public User(String username, String password, String email, String name,
                String lastName, String dni, String phone, LocalDate birthday, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30,message = "Username name must be less than 30 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120,message = "Password must be less than 120 characters")
    @JsonIgnore
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid Email")
    @Size(max = 100)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50)
    private String name;

    @NotNull(message = "LastName cannot be null")
    @NotBlank(message = "LastName cannot be blank")
    @Size(max = 50)
    private String lastName;

    @Column(unique = true)
    @NotNull(message = "Dni cannot be null")
    @NotBlank(message = "Dni cannot be blank")
    @Size(max = 10, min = 8)
    private String dni;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 12, min = 9)
    private String phone;

    @Column(nullable = true)
    private LocalDate birthday;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 150)
    private String address;

    private Boolean banned;

    private Short creditHours;

    //Additional attribute for Student User (elementary, college, etc)
    private String educationalStage;

    //Additional attribute for Teacher User
    private BigDecimal averageStars;

    //Additional attribute for Teacher User
    private Boolean active;

    //Additional attribute for Teacher User
    private String linkedin;

    //Additional attribute for Teacher User
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "assignations",
               joinColumns = {@JoinColumn(name = "teacher_id")}, inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courses;
}
