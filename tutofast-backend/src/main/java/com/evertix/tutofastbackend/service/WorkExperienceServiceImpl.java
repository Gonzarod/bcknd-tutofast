package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.WorkExperience;
import com.evertix.tutofastbackend.repository.UserRepository;
import com.evertix.tutofastbackend.repository.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService{
    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<WorkExperience> getAllWorkExperiencesByUserId(Long userId, Pageable pageable) {
        return workExperienceRepository.findAllByUser(userId, pageable);
    }

    @Override
    public WorkExperience createWorkExperience(Long userId, WorkExperience workExperience) {
        return userRepository.findById(userId).map(user -> {
            workExperience.setUser(user);
            return workExperienceRepository.save(workExperience);
        }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+userId+" not found"));
    }

    @Override
    public WorkExperience updateWorkExperience(Long userId, Long workExperienceId, WorkExperience workExperienceDetails) {
        return workExperienceRepository.findById(workExperienceId).map(workExperience -> {
            workExperience.setStart_at(workExperienceDetails.getStart_at());
            workExperience.setEnd_at(workExperienceDetails.getEnd_at());
            workExperience.setWorkplace(workExperienceDetails.getWorkplace());
            return workExperienceRepository.save(workExperience);
        }).orElseThrow(()-> new ResourceNotFoundException("WorkExperience with Id: "+workExperienceId+" not found"));
    }

    @Override
    public ResponseEntity<?> deleteWorkExperience(Long userId, Long workExperienceId) {
        return workExperienceRepository.findById(workExperienceId).map(workExperience -> {
            workExperienceRepository.delete(workExperience);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("WorkExperience with Id: "+workExperienceId+" not found"));
    }
}
