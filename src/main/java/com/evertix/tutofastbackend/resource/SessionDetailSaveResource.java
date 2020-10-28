package com.evertix.tutofastbackend.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SessionDetailSaveResource {
    @NotNull(message = "State cannot be null")
    @NotBlank(message = "State cannot be blank")
    @Size(max = 80)
    private String state;
}
