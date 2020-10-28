package com.evertix.tutofastbackend.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "complaints")
@Getter
@Setter
public class Complaint extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 250)
    private String reason;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 250)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "madeBy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    //@JsonIgnore
    private User madeBy;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "reported_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    //@JsonIgnore
    private User reported;


}
