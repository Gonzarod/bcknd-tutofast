package com.evertix.tutofastbackend.resource;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PlanResource {
    private Long id;
    private String title;
    private String period;
    private String description;
    private Short hours;
    private BigDecimal price;
    private Boolean available;
    @JsonFormat(timezone = "GMT-05:00",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(timezone = "GMT-05:00",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

}
