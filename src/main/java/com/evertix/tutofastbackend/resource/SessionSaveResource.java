package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.EStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionSaveResource {

    @Column(nullable = false, updatable = false)
    private LocalDateTime start_at;

    @Column(nullable = false, updatable = false)
    private LocalDateTime end_at;

    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 150)
    private String topic;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EStatus status;

}
