package com.evertix.tutofastbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "work_experiences")
@Getter
@Setter
public class WorkExperience extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDate start_at;

    @Column(nullable = false, updatable = false)
    private LocalDate end_at;

    @NotNull(message = "Workplace cannot be null")
    @NotBlank(message = "Workplace cannot be null")
    @Size(max = 50)
    private String workplace;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private User user;

    public WorkExperience(LocalDate start_at, LocalDate end_at, String workplace) {
        this.start_at=start_at;
        this.end_at=end_at;
        this.workplace=workplace;
    }

    public WorkExperience() {

    }
}
