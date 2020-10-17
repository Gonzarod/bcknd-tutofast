package com.evertix.tutofastbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor //Request by Model Mapper
@Getter
@Setter
public class Course extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 35)
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 250)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "courses")
    @JsonIgnore
    private List<User> teachers;

    public Course(String name, String description) {
        this.name=name;
        this.description=description;
    }
}
