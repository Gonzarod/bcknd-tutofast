package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDetailsResource {
    private Long id;
    private String state;
    private Session session;
    private User teacher;
}
