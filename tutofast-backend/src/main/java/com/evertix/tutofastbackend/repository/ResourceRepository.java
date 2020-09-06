package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Page<Resource> findAllByCourseId(Long courseId, Pageable pageable);
    Page<Resource> findAllBySessionId(Long sessionId, Pageable pageable);
}
