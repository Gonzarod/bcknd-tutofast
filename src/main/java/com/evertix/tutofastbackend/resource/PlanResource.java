package com.evertix.tutofastbackend.resource;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class PlanResource {
    private Long id;
    private String title;
    private String period;
    private String description;
    private Short hours;
    private BigDecimal price;
}
