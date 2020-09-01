package com.evertix.tutofastbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @NotBlank(message = "Name cannot be null")
    @Size(max = 100)
    private String username;


    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be null")
    @Size(max = 100)
    private String password;


    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be null")
    @Size(max = 100)
    private String name;


    @NotNull(message = "LastName cannot be null")
    @NotBlank(message = "LastName cannot be null")
    @Size(max = 100)
    private String lastName;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;



    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be null")
    @Size(max = 12)
    private String phone;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be null")
    @Size(max = 150)
    private String address;

    private int totalStar;

    private Boolean active;
}
