package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.repository.PlanRepository;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import com.evertix.tutofastbackend.service.PlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    PlanRepository planRepository;

    @Override
    public Page<PlanResource> getAllPlans(Pageable pageable) {
        Page<Plan> planPage = planRepository.findAll(pageable);
        List<PlanResource> resources = planPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, planPage.getTotalElements());
    }

    @Override
    public List<PlanResource> getAvailablePlans() {
        List<Plan> plans=planRepository.getAllByAvailableTrue();
        return plans.stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Override
    public PlanResource getPlanById(Long planId) {
        return planRepository.findById(planId).map(this::convertToResource).orElseThrow(()->
                new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
    }

    @Override
    public PlanResource createPlan(PlanSaveResource plan) {
        return convertToResource(planRepository.save(convertToEntity(plan)));
    }

    @Override
    public PlanResource updatePlan(Long planId, PlanSaveResource planDetails) {
        return planRepository.findById(planId).map(plan -> {
             plan.setTitle(planDetails.getTitle());
             plan.setPeriod(planDetails.getPeriod());
             plan.setDescription(planDetails.getDescription());
             plan.setHours(planDetails.getHours());
             plan.setPrice(planDetails.getPrice());
             plan.setAvailable(planDetails.getAvailable());
             return convertToResource(planRepository.save(plan));
        }).orElseThrow(()->new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
    }

    @Override
    public ResponseEntity<?> deletePlan(Long planId) {
        return planRepository.findById(planId).map(plan -> {
            planRepository.delete(plan);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
    }

    @Override
    public List<PlanResource> makePlansAvailable(List<Long> plansIds) {
        List<Plan> plans=new ArrayList<>();
        for (Long planId:plansIds){
            Plan plan=planRepository.findById(planId).orElseThrow(()->new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
            plan.setAvailable(true);
            plans.add(plan);
        }
        return this.planRepository.saveAll(plans).stream().map(this::convertToResource).collect(Collectors.toList());

    }

    @Override
    public List<PlanResource> makePlansNonAvailable(List<Long> plansIds) {
        List<Plan> plans=new ArrayList<>();
        for (Long planId:plansIds){
            Plan plan=planRepository.findById(planId).orElseThrow(()->new ResourceNotFoundException("Plan with Id: "+planId+" not found"));
            plan.setAvailable(false);
            plans.add(plan);
        }
        return this.planRepository.saveAll(plans).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    private Plan convertToEntity(PlanSaveResource resource){return mapper.map(resource, Plan.class);}
    private PlanResource convertToResource(Plan entity){return mapper.map(entity, PlanResource.class);}
}
