package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import com.evertix.tutofastbackend.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Plan", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PlanController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PlanService planService;

    @GetMapping("/plans")
    @Operation(summary = "Get All Plans", description = "Get Plans", tags = {"Plan"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Page you want to retrieve (0..N)"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Number of records per page."
                            , name = "size"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. " + "Multiple sort criteria are supported."
                            , name = "sort"
                            , content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
            })
    public Page<PlanResource> getAllPlans(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Plan> planPage = planService.getAllPlans(pageable);
        List<PlanResource> resources = planPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        // Pageable page = PageRequest.of(planPage.getSize(), planPage.getTotalPages());
        return new PageImpl<>(resources, pageable, planPage.getTotalElements());
    }

    @GetMapping("/plans/{planId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get Plan by Id", description = "Get Plan by Id", tags = {"Plan"})
    public PlanResource getUserById(@PathVariable(name = "planId") Long planId){
        return convertToResource(planService.getPlanById(planId));
    }

    //TODO
    //Page<Plan> getPlansByRole(String role,Pageable pageable);

    @PostMapping("/plans")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Post Plan", description = "Create Plan", tags = {"Plan"})
    public PlanResource createPlan(@Valid @RequestBody PlanSaveResource resource){
        return convertToResource(planService.createPlan(convertToEntity(resource)));
    }

    @PutMapping("/plans/{planId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Put Plan", description = "Update User", tags = {"Plan"})
    public PlanResource updatePlan(@PathVariable(name = "planId") Long planId,
                                   @Valid @RequestBody PlanSaveResource resource){
        return convertToResource(planService.updatePlan(planId, convertToEntity(resource)));
    }

    @DeleteMapping("/plans/{planId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Plan", description = "Delete Plan", tags = {"Plan"})
    public ResponseEntity<?> deletePlan(@PathVariable(name = "planId") Long planId){
        return planService.deletePlan(planId);
    }

    /*
    *
    * */

    private Plan convertToEntity(PlanSaveResource resource){return mapper.map(resource, Plan.class);}
    private PlanResource convertToResource(Plan entity){return mapper.map(entity, PlanResource.class);}
}
