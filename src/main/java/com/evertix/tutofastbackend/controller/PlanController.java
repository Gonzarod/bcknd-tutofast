package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import com.evertix.tutofastbackend.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Plan", description = "API is Ready")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/plans")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get All Plans", description = "Get all plans. Endpoint can be accessed by role admin.",
            tags = {"Plan"},
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
            },security = @SecurityRequirement(name = "bearerAuth"))
    public Page<PlanResource> getAllPlans(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.planService.getAllPlans(pageable);
    }

    @GetMapping("/plans/available")
    @Operation(summary = "Get All Available Plans", description = "Get all available Plans. Endpoint is public.",
            tags = {"Plan"})
    public List<PlanResource> getAvailablePlans(){
        return this.planService.getAvailablePlans();
    }

    @GetMapping("/plans/{planId}")
    @Operation(summary = "Get Plan by Id", description = "Get Plan by Id. Endpoint can be accessed by any role.",tags = {"Plan"})
    public PlanResource getPlanById(@PathVariable(name = "planId") Long planId){
        return planService.getPlanById(planId);
    }

    @PostMapping("/plans")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Post Plan", description = "Create Plan. Endpoint can only be accessed by admin role",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"Plan"})
    public PlanResource createPlan(@Valid @RequestBody PlanSaveResource newPlan){
        return planService.createPlan(newPlan);
    }

    @PutMapping("/plans/{planId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Put Plan", description = "Update User. Endpoint can only be accessed by admin role",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"Plan"})
    public PlanResource updatePlan(@PathVariable(name = "planId") Long planId,
                                   @Valid @RequestBody PlanSaveResource planDetails){
        return planService.updatePlan(planId,planDetails);
    }

    @PutMapping("/plans/available")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Makes available a list of plans", description = "Makes available a list of Plans. ID's of plans are required",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"Plan"})
    public List<PlanResource> makePlansAvailable(@RequestBody List<Long> plansIds){
        return planService.makePlansAvailable(plansIds);
    }

    @PutMapping("/plans/unavailable")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Makes unavailable a list of plans", description = "Makes unavailable a list of Plans. ID's of plans are required",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Plan"})
    public List<PlanResource> makePlansNonAvailable(@RequestBody List<Long> plansIds){
        return planService.makePlansNonAvailable(plansIds);
    }

    @DeleteMapping("/plans/{planId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete Plan", description = "Delete Plan. Endpoint can only be accessed by admin role",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"Plan"})
    public ResponseEntity<?> deletePlan(@PathVariable(name = "planId") Long planId){
        return planService.deletePlan(planId);
    }

}
