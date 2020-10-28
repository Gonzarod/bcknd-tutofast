package com.evertix.tutofastbackend.service;

    import com.evertix.tutofastbackend.resource.ComplaintResource;
import com.evertix.tutofastbackend.resource.ComplaintSaveResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComplaintService {

    List<ComplaintResource> getAllComplaints();
    Page<ComplaintResource> getAllComplaintsPage(Pageable pageable);
    Page<ComplaintResource> getAllComplaintsByMadeById(Long madeById, Pageable pageable);
    List<ComplaintResource> getAllComplaintsByMadeById(Long madeById);
    Page<ComplaintResource> getAllComplaintsByReportedId(Long reportedId, Pageable pageable);
    List<ComplaintResource> getAllComplaintsByReportedId(Long reportedId);
    ComplaintResource createComplaint(Long madeById,Long reportedId, ComplaintSaveResource complaint);

    List<String> getListOfReasons(String reason);


    ComplaintResource getComplaintById(Long complaintId);
}
