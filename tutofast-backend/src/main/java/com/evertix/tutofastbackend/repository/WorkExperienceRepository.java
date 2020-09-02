package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.WorkExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    Page<WorkExperience> findAllByUser(Long userId, Pageable pageable);
}
