package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.SessionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessionDetailService {



    Page<SessionDetail> getAllSessionDetailsBySessionId(Long sessionId, Pageable pageable);
    List<SessionDetail> getAllSessionDetailsBySessionId(Long sessionId);
    SessionDetail createSessionDetail(Long sessionId, Long teacherId, SessionDetail sessionDetail);
    SessionDetail acceptTeacher(Long sessionDetailId);
    SessionDetail updateSessionDetail(Long sessionId, Long teacherId, Long sessionDetailId, SessionDetail sessionDetailDetails);
    ResponseEntity<?> deleteSessionDetail(Long sessionId, Long teacherId, Long sessionDetailId);
}
