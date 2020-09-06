package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SessionService {
    Page<Session> getAllSessionsByStudentId(Long studentId, Pageable pageable);
    Page<Session> getAllSessionsByCourseName(String courseName, Pageable pageable);
    Page<Session> getAllSessionsByStatus(String status, Pageable pageable);
    Session createSession(Long courseId, Long studentId, Session session);
    Session updateSession(Long courseId, Long studentId, Long sessionId, Session sessionDetails);
    ResponseEntity<?> deleteSession(Long courseId, Long studentId, Long sessionId);
}
