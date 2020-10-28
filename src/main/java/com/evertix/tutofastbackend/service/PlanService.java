package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlanService {
    Page<PlanResource> getAllPlans(Pageable pageable);
    List<PlanResource> getAvailablePlans();
    PlanResource getPlanById(Long planId);
    PlanResource createPlan(PlanSaveResource newPlan);
    PlanResource updatePlan(Long planId, PlanSaveResource planDetails);
    ResponseEntity<?> deletePlan(Long planId);

    List<PlanResource> makePlansAvailable(List<Long> plansIds);
    List<PlanResource> makePlansNonAvailable(List<Long> plansIds);
}
