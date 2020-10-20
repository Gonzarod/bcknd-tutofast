package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.Course;
import com.evertix.tutofastbackend.model.Session;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceResource {
    private Long id;
    private String tittle;
    private String description;
    private String content;
    private Session session;
    private Course course;
}
