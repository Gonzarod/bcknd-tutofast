package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.SessionDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionDetailRepository extends JpaRepository<SessionDetail, Long> {
    Page<SessionDetail> findAllBySessionId(Long sessionId, Pageable pageable);
    List<SessionDetail> findAllBySessionId(Long sessionId);
}
