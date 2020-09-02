package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WorkExperienceResource {
    private Long id;
    private Date start_at;
    private Date end_at;
    private String workplace;
    private User user;
}
