package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.model.ERole;
import com.evertix.tutofastbackend.model.Role;
import com.evertix.tutofastbackend.model.User;
import com.evertix.tutofastbackend.repository.RoleRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import com.evertix.tutofastbackend.resource.UserResource;
import com.evertix.tutofastbackend.security.jwt.JwtUtils;
import com.evertix.tutofastbackend.security.payload.request.LoginRequest;
import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;
import com.evertix.tutofastbackend.security.payload.response.JwtResponse;
import com.evertix.tutofastbackend.security.payload.response.MessageResponse;
import com.evertix.tutofastbackend.security.service.UserDetailsImpl;
import com.evertix.tutofastbackend.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getName(),
                signUpRequest.getLastName(), signUpRequest.getDni(), signUpRequest.getPhone(), signUpRequest.getBirthday(), signUpRequest.getAddress());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Role must not be null ):"));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_STUDENT":
                        Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role student is not found. :("));
                        roles.add(studentRole);

                        break;
                    case "ROLE_TEACHER":
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role Teacher is not found. :("));
                        roles.add(teacherRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_NONE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(mapper.map(user, UserResource.class));
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.getName(),
                userDetails.getLastName(), userDetails.getDni(), userDetails.getPhone(), userDetails.getBirthday(), userDetails.getAddress()));
    }


}
