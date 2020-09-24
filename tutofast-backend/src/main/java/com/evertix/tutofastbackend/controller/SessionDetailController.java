package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.SessionDetail;
import com.evertix.tutofastbackend.resource.SessionDetailResource;
import com.evertix.tutofastbackend.resource.SessionDetailSaveResource;
import com.evertix.tutofastbackend.service.SessionDetailService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "SessionDetail", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionDetailController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SessionDetailService sessionDetailService;

    @GetMapping("/sessions/{sessionId}/sessionDetails")
    @Operation(summary = "Get All SessionDetails By Session", description = "Get All SessionDetails By Session", tags = {"SessionDetail"},
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
    public Page<SessionDetailResource> getAllSessionDetailsBySessionId(@PathVariable(name = "sessionId") Long sessionId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<SessionDetail> sessionDetailPage = sessionDetailService.getAllSessionDetailsBySessionId(sessionId, pageable);
        List<SessionDetailResource> resources = sessionDetailPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,sessionDetailPage.getTotalElements());
    }

    @PostMapping("/sessions/{sessionId}/teachers/{teacherId}/sessionDetails")
    @Operation(summary = "Post SessionDetail", description = "Create SessionDetail", tags = {"SessionDetail"})
    public SessionDetailResource createSessionDetail(@PathVariable(name = "sessionId") Long sessionId,
                                                     @PathVariable(name = "teacherId") Long teacherId,
                                                     @Valid @RequestBody SessionDetailSaveResource resource){
        return convertToResource(sessionDetailService.createSessionDetail(sessionId,teacherId,convertToEntity(resource)));
    }

    @PutMapping("/sessions/{sessionId}/teachers/{teacherId}/sessionDetails/{sessionDetailId}")
    @Operation(summary = "Put SessionDetail", description = "Update SessionDetail", tags = {"SessionDetail"})
    public SessionDetailResource updateSessionDetail(@PathVariable(name = "sessionId") Long sessionId,
                                                     @PathVariable(name = "teacherId") Long teacherId,
                                                     @PathVariable(name = "sessionDetailId") Long sessionDetailId,
                                                     @Valid @RequestBody SessionDetailSaveResource resource){
        return convertToResource(sessionDetailService.updateSessionDetail(sessionId,teacherId,sessionDetailId,convertToEntity(resource)));
    }

    @DeleteMapping("/sessions/{sessionId}/teachers/{teacherId}/sessionDetails/{sessionDetailId}")
    @Operation(summary = "Delete SessionDetail", description = "Delete SessionDetail", tags = {"SessionDetail"})
    ResponseEntity<?> deleteSessionDetail(@PathVariable(name = "sessionId") Long sessionId,
                                          @PathVariable(name = "teacherId") Long teacherId,
                                          @PathVariable(name = "sessionDetailId") Long sessionDetailId){
        return sessionDetailService.deleteSessionDetail(sessionId, teacherId, sessionDetailId);
    }

    private SessionDetail convertToEntity(SessionDetailSaveResource resource){return mapper.map(resource, SessionDetail.class);}
    private SessionDetailResource convertToResource(SessionDetail entity){return mapper.map(entity, SessionDetailResource.class);}
}
