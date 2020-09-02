package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    Page<User> getAllUsersByRolId(Long rolId, Pageable pageable);
    User getUserById(Long userId);
    User createUser(Long rolId, User user);
    User updateUser(Long rolId, Long userId, User userDetails);
    ResponseEntity<?> deleteUser(Long rolId, Long userId);
}
