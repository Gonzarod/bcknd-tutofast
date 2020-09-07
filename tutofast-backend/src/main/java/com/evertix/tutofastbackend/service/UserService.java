package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //Page<User> getAllUsersByRole(String role, Pageable pageable);
    Page<User> getAllUsersByCourseId(Long courseId, Pageable pageable);
    User getUserById(Long userId);
    User createUser(User user);
    User updateUser(Long userId, User userDetails);
    ResponseEntity<?> deleteUser(Long userId);

}
