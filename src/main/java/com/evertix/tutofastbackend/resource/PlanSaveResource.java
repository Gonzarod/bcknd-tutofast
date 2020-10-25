package com.evertix.tutofastbackend.resource;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlanSaveResource {

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

    @DecimalMin(value = "0.00")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    private Boolean available;
}
