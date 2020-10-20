package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.EStatus;
import com.evertix.tutofastbackend.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Page<Session> getAllByStudentIdAndStatusEquals(Long studentId, EStatus status,Pageable pageable);
    Page<Session> getAllByStudentId(Long studentId, Pageable pageable);
    Page<Session> getAllByCourseName(String courseName, Pageable pageable);
    //Page<Session> getAllByStatus(EStatus status, Pageable pageable);
}
