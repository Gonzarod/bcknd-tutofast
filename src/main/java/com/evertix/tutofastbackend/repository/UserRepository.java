package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Role;
import com.evertix.tutofastbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    /*Page<User> findAllByRoles(Set<Role> roles, Pageable pageable);

     */
    @Query(value = "select table1.* from users table1 inner join user_roles table2 on table1.id=table2.user_id inner join roles table3 on table2.role_id=table3.id where table3.name=?1"
            ,nativeQuery = true)
    List<User> getAllUserByRole(String role);


}
