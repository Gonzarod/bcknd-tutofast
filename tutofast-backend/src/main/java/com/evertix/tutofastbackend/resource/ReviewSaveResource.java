package com.evertix.tutofastbackend.resource;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
public class ReviewSaveResource {

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be null")
    @Size(max = 250)
    private String description;

    // ONLY FOR STUDENT
    @Max(value = 5)
    private Short stars;


}
