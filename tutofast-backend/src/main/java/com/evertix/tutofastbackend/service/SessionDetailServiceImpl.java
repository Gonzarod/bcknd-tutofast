package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.SessionDetail;
import com.evertix.tutofastbackend.repository.SessionDetailRepository;
import com.evertix.tutofastbackend.repository.SessionRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionDetailServiceImpl implements SessionDetailService{
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionDetailRepository sessionDetailRepository;

    @Override
    public Page<SessionDetail> getAllSessionDetailsBySessionId(Long sessionId, Pageable pageable) {
        return sessionDetailRepository.findAllBySessionId(sessionId, pageable);
    }

    @Override
    public SessionDetail createSessionDetail(Long sessionId, Long teacherId, SessionDetail sessionDetail) {
        return sessionRepository.findById(sessionId).map(session -> {
            return userRepository.findById(teacherId).map(user -> {
                sessionDetail.setTeacher(user);
                sessionDetail.setSession(session);
                return sessionDetailRepository.save(sessionDetail);
            }).orElseThrow(()-> new ResourceNotFoundException("Teacher with Id: "+teacherId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
    }

    @Override
    public SessionDetail updateSessionDetail(Long sessionId, Long teacherId, Long sessionDetailId, SessionDetail sessionDetailDetails) {
        return sessionRepository.findById(sessionId).map(session -> {
            return userRepository.findById(teacherId).map(user -> {
                return sessionDetailRepository.findById(sessionDetailId).map(sessionDetail -> {
                    sessionDetail.setTeacher(user);
                    sessionDetail.setSession(session);
                    sessionDetail.setState(sessionDetailDetails.getState());
                    return sessionDetailRepository.save(sessionDetail);
                }).orElseThrow(()-> new ResourceNotFoundException("SessionDetail with Id: "+sessionDetailId+" not found"));
            }).orElseThrow(()-> new ResourceNotFoundException("Teacher with Id: "+teacherId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
    }

    @Override
    public ResponseEntity<?> deleteSessionDetail(Long sessionId, Long teacherId, Long sessionDetailId) {
        return sessionRepository.findById(sessionId).map(session -> {
            return userRepository.findById(teacherId).map(user -> {
                return sessionDetailRepository.findById(sessionDetailId).map(sessionDetail -> {
                    sessionDetailRepository.delete(sessionDetail);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("SessionDetail with Id: "+sessionDetailId+" not found"));
            }).orElseThrow(()-> new ResourceNotFoundException("Teacher with Id: "+teacherId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
    }
}
