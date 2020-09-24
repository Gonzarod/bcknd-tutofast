package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Page<Complaint> findAllByMadeById(Long madeById, Pageable pageable);
    Page<Complaint> findAllByReportedId(Long reportedId, Pageable pageable);
}
