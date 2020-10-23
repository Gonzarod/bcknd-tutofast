package com.evertix.tutofastbackend.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SessionResource {
    private Long id;
    private Date start_at;
    private Date end_at;
    private String status;
    private String topic;
    private String link;
    private StudentResource student;
    private CourseResource course;
}
