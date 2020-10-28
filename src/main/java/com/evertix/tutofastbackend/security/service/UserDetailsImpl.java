package com.evertix.tutofastbackend.security.service;

import com.evertix.tutofastbackend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    public UserDetailsImpl(Long id, String username, String password, String email, String name, String lastName,
                           String dni, String phone, LocalDate birthday, String address, Boolean active,Boolean banned, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.active=active;
        this.banned=banned;
        this.authorities = authorities;
    }

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final String email;

    private final String name;

    private final String lastName;

    private final String dni;

    private final String phone;

    private final LocalDate birthday;

    private final String address;

    private final Boolean active;

    private final Boolean banned;

    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getName(),
                                   user.getLastName(), user.getDni(), user.getPhone(), user.getBirthday(), user.getAddress(), user.getActive(),user.getBanned(),authorities);

    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() { return name; }

    public String getLastName() { return lastName; }

    public String getDni() { return dni; }

    public String getPhone() { return phone; }

    public LocalDate getBirthday() { return birthday; }

    public String getAddress() { return address; }

    public Boolean getActive() {
        return active;
    }

    public Boolean getBanned() {
        return banned;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
