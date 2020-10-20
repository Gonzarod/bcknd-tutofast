package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubscriptionResource {

    private Long id;

    private Date createdAt;

    private Boolean active;

    private User user;

    private Plan plan;

}
