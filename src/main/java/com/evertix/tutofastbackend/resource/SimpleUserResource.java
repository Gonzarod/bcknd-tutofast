package com.evertix.tutofastbackend.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SimpleUserResource {
    private Long id;
    private String username;
    private String name;
    private String lastName;
}
