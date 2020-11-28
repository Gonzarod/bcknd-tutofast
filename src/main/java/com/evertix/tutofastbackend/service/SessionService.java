package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.resource.SessionResource;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessionService {
    Page<SessionResource> getAllSessions(Pageable pageable);
    ResponseEntity<?> createSessionRequest(Long courseId, Long studentId, SessionSaveResource session);
    List<Session> getAllOpenSessionRequest();
    List<Session> getAllFinishedSession();
    List<Session> getAllOpenSessionRequestsByStudentId(Long studentId);
    List<Session> getAllClosedSessionRequestsByStudentId(Long studentId);
    List<Session> getAllFinishedAndRatedSessionRequestsByStudentId(Long studentId);
    List<Session> getAllFinishedAndNoRatedSessionRequestsByStudentId(Long studentId);
    List<Session> getAllOpenRequestFiltered(Long courseId);
    ResponseEntity<?> applyToSession(Long sessionId, Long teacherId);


    ResponseEntity<?> acceptTeacher(Long sessionDetailId);

    Page<Session> getAllSessionsByStudentId(Long studentId, Pageable pageable);
    Page<Session> getAllSessionsByCourseName(String courseName, Pageable pageable);
    //Page<Session> getAllSessionsByStatus(String status, Pageable pageable);

    //ResponseEntity<?> applyToSessionRequest(Long teacherId,Session session);
    Session createSession(Long courseId, Long studentId, Session session);
    Session updateSession(Long courseId, Long studentId, Long sessionId, Session sessionDetails);
    ResponseEntity<?> deleteSession(Long courseId, Long studentId, Long sessionId);



}
