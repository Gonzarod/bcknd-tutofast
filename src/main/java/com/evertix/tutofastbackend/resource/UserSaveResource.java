package com.evertix.tutofastbackend.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSaveResource {
    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30,message = "Username name must be less than 30 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120,message = "Password must be less than 120 characters")
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid Email")
    @Size(max = 100)
    private String email;

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

    @Column(unique = true)
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

    @Size(min = 20, max = 999)
    private int totalStar;

    private Boolean active;

    @Size(max = 50)
    private String linkedin;
}
