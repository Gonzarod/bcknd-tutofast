package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.resource.SessionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SessionService {
    Page<Session> getAllSessionsByStudentId(Long studentId, Pageable pageable);
    Page<Session> getAllSessionsByCourseName(String courseName, Pageable pageable);
    //Page<Session> getAllSessionsByStatus(String status, Pageable pageable);

    //ResponseEntity<?> applyToSessionRequest(Long teacherId,Session session);
    Session createSession(Long courseId, Long studentId, Session session);
    Session updateSession(Long courseId, Long studentId, Long sessionId, Session sessionDetails);
    ResponseEntity<?> deleteSession(Long courseId, Long studentId, Long sessionId);

    ResponseEntity<?> createSessionRequest(Long courseId,Long studentId,Session session);

    Page<Session> getAllOpenSessionRequestsByStudentId(Long studentId,Pageable pageable);
    Page<Session> getAllClosedSessionRequestsByStudentId(Long studentId,Pageable pageable);
    Page<Session> getAllFinishedAndRatedSessionRequestsByStudentId(Long studentId,Pageable pageable);
    Page<Session> getAllFinishedAndNoRatedSessionRequestsByStudentId(Long studentId,Pageable pageable);

    ResponseEntity<?> applyToSession(Long sessionId, Long teacherId);

    ResponseEntity<?> acceptTeacher(Long sessionDetailId);
}
