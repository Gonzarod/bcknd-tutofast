package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.resource.SessionResource;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import com.evertix.tutofastbackend.service.SessionService;
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

@Tag(name = "Session", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/students/{studentId}/sessions")
    @Operation(summary = "Get All Sessions By Student", description = "Get All Sessions By Student", tags = {"Session"},
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
    public Page<SessionResource> getAllSessionsByStudentId(@PathVariable(name = "studentId") Long studentId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Session> sessionPage = sessionService.getAllSessionsByStudentId(studentId, pageable);
        List<SessionResource> resources = sessionPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,sessionPage.getTotalElements());
    }

    @GetMapping("/courses/{courseName}/sessions")
    @Operation(summary = "Get All Sessions By Course", description = "Get All Sessions By Course", tags = {"Session"},
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
    public Page<SessionResource> getAllSessionsByCourseName(@PathVariable(name = "courseName") String courseName, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Session> sessionPage = sessionService.getAllSessionsByCourseName(courseName, pageable);
        List<SessionResource> resources = sessionPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,sessionPage.getTotalElements());
    }

    @GetMapping("/status/{statusName}/sessions")
    @Operation(summary = "Get All Sessions By Status", description = "Get All Sessions By Status", tags = {"Session"},
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
    public Page<SessionResource> getAllSessionsByStatus(@PathVariable(name = "statusName") String courseStatus, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Session> sessionPage = sessionService.getAllSessionsByStatus(courseStatus, pageable);
        List<SessionResource> resources = sessionPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,sessionPage.getTotalElements());
    }

    @PostMapping("/courses/{courseId}/students/{studentId}/sessions")
    @Operation(summary = "Post Session", description = "Create Session", tags = {"Session"})
    public SessionResource createSession(@PathVariable(name = "courseId") Long courseId,
                                         @PathVariable(name = "studentId") Long studentId,
                                         @Valid @RequestBody SessionSaveResource resource){
        return convertToResource(sessionService.createSession(courseId, studentId, convertToEntity(resource)));
    }

    @PutMapping("/courses/{courseId}/students/{studentId}/sessions/{sessionId}")
    @Operation(summary = "Put Session", description = "Update Session", tags = {"Session"})
    public SessionResource updateSession(@PathVariable(name = "courseId") Long courseId,
                                         @PathVariable(name = "studentId") Long studentId,
                                         @PathVariable(name = "sessionId") Long sessionId,
                                         @Valid @RequestBody SessionSaveResource resource){
        return convertToResource(sessionService.updateSession(courseId, studentId, sessionId, convertToEntity(resource)));
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}/sessions/{sessionId}")
    @Operation(summary = "Delete Session", description = "Delete Session", tags = {"Session"})
    public ResponseEntity<?> deleteSession(@PathVariable(name = "courseId") Long courseId,
                                           @PathVariable(name = "studentId") Long studentId,
                                           @PathVariable(name = "sessionId") Long sessionId){
        return sessionService.deleteSession(courseId, studentId, sessionId);
    }

    private Session convertToEntity(SessionSaveResource resource){return mapper.map(resource, Session.class);}
    private SessionResource convertToResource(Session entity){return mapper.map(entity, SessionResource.class);}
}
