package com.evertix.tutofastbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plans")
@Getter
@Setter
public class Plan extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tittle cannot be null")
    @NotBlank(message = "Tittle cannot be null")
    @Size(max = 150)
    private String tittle;

    // ONLY FOR STUDENT
    @Max(value = 500)
    private Short hours;

    @DecimalMin(value = "0.0")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be null")
    @Size(max = 150)
    private String role;

}
