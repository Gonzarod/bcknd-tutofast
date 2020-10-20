package com.evertix.tutofastbackend.service;


import com.evertix.tutofastbackend.model.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ComplaintService {

    Page<Complaint> getAllComplaints(Pageable pageable);
    Page<Complaint> getAllComplaintsByMadeById(Long madeById, Pageable pageable);
    Page<Complaint> getAllComplaintsByReportedId(Long reportedId, Pageable pageable);
    Complaint createComplaint(Long madeById,Long reportedId, Complaint complaint);
    Complaint updateComplaint(Long complaintId, Complaint complaintDetails);
    ResponseEntity<?> deleteComplaint(Long complaintId);
}
