package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.resource.*;
import com.evertix.tutofastbackend.resource.ComplaintResource;
import com.evertix.tutofastbackend.service.ComplaintService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Complaint", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/complaints/page")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints Page", description = "Get Complaints Page", tags = {"Complaint"},
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
    public Page<ComplaintResource> getAllComplaintsPaginated(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.complaintService.getAllComplaintsPage(pageable);
    }

    @GetMapping("/complaints/{complaintId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints", description = "Get All Complaints", tags = {"Complaint"},
            security = @SecurityRequirement(name = "bearerAuth"))
    public ComplaintResource getComplaintById(@PathVariable Long complaintId){
        return this.complaintService.getComplaintById(complaintId);
    }

    @GetMapping("/complaints/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints", description = "Get All Complaints", tags = {"Complaint"},
               security = @SecurityRequirement(name = "bearerAuth"))
    public List<ComplaintResource> getAllComplaints(){
        return this.complaintService.getAllComplaints();
    }

    /*
    //TODO: CHECK USAGE
    @GetMapping("/complaints/reasons")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Reasons",
               description = "Get all reasons. Can be  filtered by name (query param optional)", tags = {"Complaint"},security = @SecurityRequirement(name = "bearerAuth"))
    public List<String> getListOfReasons(@RequestParam(required = false) @Parameter(name="Course name",description = "is Optional") String reason){
        return this.complaintService.getListOfReasons(reason);
    }
    */

    @GetMapping("complaints/madeBy/{madeById}/page")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints By MadeBy", description = "Get All Complaints By MadeBy", tags = {"Complaint"},
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
    public Page<ComplaintResource> getAllComplaintsByMadeByIdPaginated(@PathVariable(name = "madeById") Long madeById, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.complaintService.getAllComplaintsByMadeById(madeById, pageable);
    }

    @GetMapping("complaints/madeBy/{madeById}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints By MadeBy", description = "Get All Complaints By MadeBy", tags = {"Complaint"},
               security = @SecurityRequirement(name = "bearerAuth"))
    public List<ComplaintResource> getAllComplaintsByMadeById(@PathVariable(name = "madeById") Long madeById){
        return this.complaintService.getAllComplaintsByMadeById(madeById);
    }

    @GetMapping("complaints/reported/{reportedId}/page")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints By Reported", description = "Get All Complaints By Reported", tags = {"Complaint"},
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
    public Page<ComplaintResource> getAllComplaintsByReportedIdPaginated(@PathVariable(name = "reportedId") Long reportedId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.complaintService.getAllComplaintsByReportedId(reportedId, pageable);
    }

    @GetMapping("complaints/reported/{reportedId}/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Complaints By Reported", description = "Get All Complaints By Reported", tags = {"Complaint"},
            security = @SecurityRequirement(name = "bearerAuth"))
    public List<ComplaintResource> getAllComplaintsByReportedId(@PathVariable(name = "madeById") Long madeById){
        return this.complaintService.getAllComplaintsByReportedId(madeById);
    }

    @PostMapping("complaints/madeBy/{madeById}/reported/{reportedId}/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create Complaint", description = "Create Complaint", tags = {"Complaint"},security = @SecurityRequirement(name = "bearerAuth"))
    public ComplaintResource createComplaint(@PathVariable(name = "madeById") Long madeById,
                                                 @PathVariable(name = "reportedId") Long reportedId,
                                                 @Valid @RequestBody ComplaintSaveResource resource){
        return this.complaintService.createComplaint(madeById, reportedId, resource);
    }




}
