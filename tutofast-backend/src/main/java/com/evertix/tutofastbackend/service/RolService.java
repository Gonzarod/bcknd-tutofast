package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RolService {
    Page<Rol> getAllRoles(Pageable pageable);
    Rol getRolById(Long rolId);
    Rol createRol(Rol rol);
    Rol updateRol(Long rolId, Rol rolDetails);
    ResponseEntity<?> deleteRol(Long rolId);
}
