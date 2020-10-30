package com.evertix.tutofastbackend.security.payload.response;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;
    private String username;
    private String email;
    ///private List<String> roles;
    private String roles;
    private String name;
    private String lastName;
    private String dni;
    private String phone;
    private LocalDate birthday;
    private String address;

    //List<String>
    public JwtResponse(String accessToken, Long id, String username, String email, String roles, String name,
                      String lastName, String dni, String phone, LocalDate birthday, String address){
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
    }
}
