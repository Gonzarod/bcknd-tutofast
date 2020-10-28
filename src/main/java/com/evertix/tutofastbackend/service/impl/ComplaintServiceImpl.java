package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Complaint;
import com.evertix.tutofastbackend.repository.ComplaintRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import com.evertix.tutofastbackend.resource.ComplaintResource;
import com.evertix.tutofastbackend.resource.ComplaintSaveResource;
import com.evertix.tutofastbackend.service.ComplaintService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<ComplaintResource> getAllComplaintsPage(Pageable pageable) {
        Page<Complaint> complaintPage = complaintRepository.findAll(pageable);
        List<ComplaintResource> complaintResources = complaintPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(complaintResources,pageable,complaintPage.getTotalElements());

    }

    @Override
    public List<ComplaintResource> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream().map(this::convertToResource).collect(Collectors.toList());

    }

    @Override
    public Page<ComplaintResource> getAllComplaintsByMadeById(Long userId, Pageable pageable) {
        Page<Complaint> complaintPage = complaintRepository.findAllByMadeById(userId, pageable);
        List<ComplaintResource> complaintResources = complaintPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(complaintResources,pageable,complaintPage.getTotalElements());
    }

    @Override
    public List<ComplaintResource> getAllComplaintsByMadeById(Long userId) {
        return complaintRepository.findAllByMadeById(userId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Override
    public Page<ComplaintResource> getAllComplaintsByReportedId(Long reportedId, Pageable pageable) {
        Page<Complaint> complaintPage = complaintRepository.findAllByReportedId(reportedId, pageable);
        List<ComplaintResource> complaintResources = complaintPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(complaintResources,pageable,complaintPage.getTotalElements());
    }

    @Override
    public List<ComplaintResource> getAllComplaintsByReportedId(Long reportedId) {
        return complaintRepository.findAllByReportedId(reportedId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Override
    public ComplaintResource createComplaint(Long madeById, Long reportedId, ComplaintSaveResource complaintDetails) {
        Complaint complaint=convertToEntity(complaintDetails);
        return userRepository.findById(madeById).map(madeBy -> userRepository.findById(reportedId).map(reported -> {
            complaint.setMadeBy(madeBy);
            complaint.setReported(reported);
            return convertToResource(complaintRepository.save(complaint));
        }).orElseThrow(()-> new ResourceNotFoundException("Reported with Id: "+reportedId+" not found"))).orElseThrow(()-> new ResourceNotFoundException("MadeBy with Id: "+madeById+" not found"));
    }

    @Override
    public List<String> getListOfReasons(String reason) {
        List<String> reasonsList=this.complaintRepository.getAllReasons();
        List<String> filteredList = new ArrayList<>();
        if(reason==null){
            return reasonsList;
        }else {
            for (String r:reasonsList){
                if(r.toLowerCase().contains(reason)){
                    filteredList.add(r);
                }
            }
            return  filteredList;
        }
    }

    @Override
    public ComplaintResource getComplaintById(Long complaintId) {
        return convertToResource(this.complaintRepository.findById(complaintId)
                .orElseThrow(()-> new ResourceNotFoundException("Complaint with id: "+complaintId+" not found")));
    }

    private Complaint convertToEntity(ComplaintSaveResource resource){return mapper.map(resource, Complaint.class);}
    private ComplaintResource convertToResource(Complaint entity){return mapper.map(entity, ComplaintResource.class);}


}
