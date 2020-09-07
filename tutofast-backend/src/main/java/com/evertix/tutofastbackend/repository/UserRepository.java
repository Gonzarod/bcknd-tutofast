package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.ERole;
import com.evertix.tutofastbackend.model.Role;
import com.evertix.tutofastbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Page<User> findAllByRole(String role, Pageable pageable);
    Page<User> findAllByRole(String role,Pageable pageable);

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findAllByCoursesId(Long courseId, Pageable pageable);
}
