package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.SessionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SessionDetailService {
    Page<SessionDetail> getAllSessionDetailsBySessionId(Long sessionId, Pageable pageable);
    SessionDetail createSessionDetail(Long sessionId, Long teacherId, SessionDetail sessionDetail);
    SessionDetail updateSessionDetail(Long sessionId, Long teacherId, Long sessionDetailId, SessionDetail sessionDetailDetails);
    ResponseEntity<?> deleteSessionDetail(Long sessionId, Long teacherId, Long sessionDetailId);
}
