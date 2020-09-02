package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.User;
import com.evertix.tutofastbackend.repository.RolRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Page<User> getAllUsersByRolId(Long rolId, Pageable pageable) { return userRepository.findAllByRole(rolId, pageable); }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User with Id: "+userId+" not found"));
    }

    @Override
    public User createUser(Long rolId, User user) {
        return rolRepository.findById(rolId).map(role -> {
            user.setRole(role);
            return userRepository.save(user);
        }).orElseThrow(()-> new ResourceNotFoundException("Role whit Id: "+rolId+" not found"));
    }

    @Override
    public User updateUser(Long rolId, Long userId, User userDetails) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setName(userDetails.getName());
            user.setLastName(userDetails.getLastName());
            user.setBirthday(userDetails.getBirthday());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setTotalStar(userDetails.getTotalStar());
            user.setActive(userDetails.getActive());
            user.setLinkedln(userDetails.getLinkedln());
            return userRepository.save(user);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found whit Id "+userId+" by Role with Id "+rolId));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long rolId, Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+userId+" not found"));
    }
}
