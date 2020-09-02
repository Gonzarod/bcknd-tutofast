package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Rol;
import com.evertix.tutofastbackend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public Page<Rol> getAllRoles(Pageable pageable) { return rolRepository.findAll(pageable); }

    @Override
    public Rol getRolById(Long rolId) {
        return rolRepository.findById(rolId).orElseThrow(()->
                new ResourceNotFoundException("Role with Id: "+rolId+" not found"));
    }

    @Override
    public Rol createRol(Rol rol) { return rolRepository.save(rol); }

    @Override
    public Rol updateRol(Long rolId, Rol roleDetails) {
        return rolRepository.findById(rolId).map(role -> {
            role.setName(roleDetails.getName());
            role.setDescription(roleDetails.getDescription());
            return rolRepository.save(role);
        }).orElseThrow(()-> new ResourceNotFoundException("Role with Id: "+rolId+" not found"));
    }

    @Override
    public ResponseEntity<?> deleteRol(Long rolId) {
        return rolRepository.findById(rolId).map(role -> {
            rolRepository.delete(role);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Role with Id: "+rolId+" not found"));
    }
}
