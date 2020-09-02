package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PlanService {
    Page<Plan> getAllPlans(Pageable pageable);
    Plan getPlanById(Long planId);
    Page<Plan> getPlansByRole(String role,Pageable pageable);
    Plan createPlan(Plan plan);
    Plan updatePlan(Long planId, Plan planDetails);
    ResponseEntity<?> deletePlan(Long planId);

}
