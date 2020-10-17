package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Complaint;
import com.evertix.tutofastbackend.repository.ComplaintRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import com.evertix.tutofastbackend.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Complaint> getAllComplaints(Pageable pageable) { return complaintRepository.findAll(pageable); }

    @Override
    public Page<Complaint> getAllComplaintsByMadeById(Long userId, Pageable pageable) {
        return complaintRepository.findAllByMadeById(userId, pageable);
    }

    @Override
    public Page<Complaint> getAllComplaintsByReportedId(Long reportedId, Pageable pageable) {
        return complaintRepository.findAllByReportedId(reportedId, pageable);
    }

    @Override
    public Complaint createComplaint(Long madeById, Long reportedId, Complaint complaint) {
        return userRepository.findById(madeById).map(madeBy -> {
            return userRepository.findById(reportedId).map(reported -> {
                complaint.setMadeBy(madeBy);
                complaint.setReported(reported);
                return complaintRepository.save(complaint);
            }).orElseThrow(()-> new ResourceNotFoundException("Reported with Id: "+reportedId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("MadeBy with Id: "+madeById+" not found"));
    }

    @Override
    public Complaint updateComplaint(Long complaintId, Complaint complaintDetails) {
        return complaintRepository.findById(complaintId).map(complaint -> {
            complaint.setSubject(complaintDetails.getSubject());
            complaint.setDescription(complaintDetails.getDescription());
            return complaintRepository.save(complaint);
        }).orElseThrow(() -> new ResourceNotFoundException("Complaint with Id: "+complaintId+" not found"));
    }

    @Override
    public ResponseEntity<?> deleteComplaint(Long complaintId) {
        return complaintRepository.findById(complaintId).map(complaint -> {
            complaintRepository.delete(complaint);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Complaint with Id: "+complaintId+" not found"));
    }



}
