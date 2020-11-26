package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Subscription;
import com.evertix.tutofastbackend.resource.SubscriptionResource;
import com.evertix.tutofastbackend.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Subscription", description = "API is Ready")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    //@PreAuthorize("isAuthenticated()")
    //security = @SecurityRequirement(name = "bearerAuth"), tags = {"Subscription"},
    @Operation(summary = "Get All Subscriptions", description = "Get All Subscription Details",
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
    public Page<SubscriptionResource> getAllSubscriptions(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Subscription> subscriptionPage = subscriptionService.getAllSubscriptions(pageable);
        List<SubscriptionResource> resources = subscriptionPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,subscriptionPage.getTotalElements());
    }

    @GetMapping("subscriptions/user/{userId}/plans")
    //@PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    //security = @SecurityRequirement(name = "bearerAuth"),
    @Operation(summary = "Get Subscription By User Id", description = "Get Subscription By User Id",
                tags = {"Subscription"})
    public Page<SubscriptionResource> getUsersSubscriptions(@PathVariable(name = "userId") Long userId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Subscription> subscriptionPage = subscriptionService.getUsersSubscriptions(userId, pageable);
        List<SubscriptionResource> resources = subscriptionPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @PostMapping("/subscriptions/userId/{userId}/plan/{planId}")
    //@PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    //security = @SecurityRequirement(name = "bearerAuth"),
    @Operation(summary = "Subscribe To Plan", description = "User Subscribe To Plan",
               tags = {"Subscription"})
    public ResponseEntity<?> subscribeToPlan(@PathVariable(name = "userId") Long userId,@PathVariable(name = "planId") Long planId){
        return subscriptionService.subscribeToPlan(userId,planId);
    }


    @PutMapping("/subscriptions/userId/{userId}/plan/{planId}")
    //@PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    //security = @SecurityRequirement(name = "bearerAuth"),
    @Operation(summary = "Unsubscribe To Plan", description = "User unsubscribe To Plan",
               tags = {"Subscription"})
    public ResponseEntity<?> unsubscribeToPlan(@PathVariable(name = "userId") Long userId,@PathVariable(name = "planId") Long planId){
        return subscriptionService.unsubscribeToPlan(userId,planId);
    }
/*
    @DeleteMapping("/subscriptions/{subscriptionId}/userId/{userId}/plan/{planId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete Subscription", description = "Delete Subscription",
                security = @SecurityRequirement(name = "bearerAuth"),tags = {"Subscription"})
    public ResponseEntity<?> deletePlan(@PathVariable(name = "userId") Long userId,@PathVariable(name = "planId") Long planId,@PathVariable(name = "subscriptionId") Long subscriptionId){
        return subscriptionService.deleteSubscription(userId,planId,subscriptionId);

    }
    */

    private SubscriptionResource convertToResource(Subscription entity){return mapper.map(entity, SubscriptionResource.class);}
}
