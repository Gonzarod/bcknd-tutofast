package com.evertix.tutofastbackend.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class SessionResource {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_at;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end_at;
    private String status;
    private String topic;
    private String link;
    private StudentResource student;
    private CourseResource course;
}
