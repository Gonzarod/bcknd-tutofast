package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResource {
    private Long id;
    private String description;
    private Short stars;
    private User student;
    private User teacher;
}
