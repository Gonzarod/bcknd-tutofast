package com.evertix.tutofastbackend.resource;

import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResource {

    private Long id;

    private Boolean active;

    private User user;

    private Plan plan;

}
