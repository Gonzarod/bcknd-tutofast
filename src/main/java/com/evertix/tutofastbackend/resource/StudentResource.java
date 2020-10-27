package com.evertix.tutofastbackend.resource;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StudentResource {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private Short creditHours;
}

