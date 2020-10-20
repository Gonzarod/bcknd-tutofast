package com.evertix.tutofastbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plans")
@NoArgsConstructor
@Getter
@Setter
public class Plan extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be null")
    @Size(max = 150)
    private String title;

    @NotNull(message = "Period cannot be null")
    @NotBlank(message = "Period cannot be null")
    @Size(max = 150)
    private String period;

    @NotNull(message = "Tittle cannot be null")
    @NotBlank(message = "Tittle cannot be null")
    @Size(max = 500)
    private String description;

    // ONLY FOR STUDENT
    @Max(value = 500)
    private Short hours;

    @DecimalMin(value = "0.0")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    public Plan(String title, String period, String description, Short hours, BigDecimal price) {
        this.title=title;
        this.period=period;
        this.description=description;
        this.hours=hours;
        this.price=price;
    }
}
