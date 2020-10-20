package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.ERole;
import com.evertix.tutofastbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
