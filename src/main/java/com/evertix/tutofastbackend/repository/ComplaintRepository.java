package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    @Query("select DISTINCT(c.reason) from Complaint c")
    List<String> getAllReasons();

    Page<Complaint> findAllByMadeById(Long madeById, Pageable pageable);
    List<Complaint> findAllByMadeById(Long madeById);
    Page<Complaint> findAllByReportedId(Long reportedId, Pageable pageable);
    List<Complaint> findAllByReportedId(Long reportedId);
}
