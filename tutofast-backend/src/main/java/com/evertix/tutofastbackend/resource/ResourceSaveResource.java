package com.evertix.tutofastbackend.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ResourceSaveResource {
    @NotNull(message = "Tittle cannot be null")
    @NotBlank(message = "Tittle cannot be blank")
    @Size(max = 150)
    private String tittle;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 150)
    private String description;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 50)
    private String content;
}
