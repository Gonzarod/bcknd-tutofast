package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ComplaintResource {

    private Long id;

    private String subject;

    private String description;

    private User madeBy;

    private User reported;
}
