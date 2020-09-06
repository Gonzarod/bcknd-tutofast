package com.evertix.tutofastbackend.resource;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlanResource {
    private Long id;
    private String tittle;
    private Short hours;
    private BigDecimal price;
    private String role;
}
