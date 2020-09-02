package com.evertix.tutofastbackend.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class WorkExperienceSaveResource {
    @Column(nullable = false, updatable = false)
    private LocalDate start_at;

    @Column(nullable = false, updatable = false)
    private LocalDate end_at;

    @NotNull(message = "Workplace cannot be null")
    @NotBlank(message = "Workplace cannot be null")
    @Size(max = 50)
    private String workplace;
}
