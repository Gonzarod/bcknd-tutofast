package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.repository.PlanRepository;
import com.evertix.tutofastbackend.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Override
    public Page<Plan> getAllPlans(Pageable pageable) {
        return planRepository.findAll(pageable);
    }

    @Override
    public Plan getPlanById(Long planId) {
        return planRepository.findById(planId).orElseThrow(()->
                new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
    }

    @Override
    public Page<Plan> getPlansByRole(String role, Pageable pageable) {
        return planRepository.findAllByRole(role,pageable);
    }

    @Override
    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    @Override
    public Plan updatePlan(Long planId, Plan planDetails) {
        return planRepository.findById(planId).map(plan -> {
             plan.setTittle(planDetails.getTittle());
             plan.setHours(planDetails.getHours());
             plan.setPrice(planDetails.getPrice());
             plan.setRole(planDetails.getRole());
             return planRepository.save(plan);
        }).orElseThrow(()->new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
    }

    @Override
    public ResponseEntity<?> deletePlan(Long planId) {
        return planRepository.findById(planId).map(plan -> {
            planRepository.delete(plan);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
    }
}
