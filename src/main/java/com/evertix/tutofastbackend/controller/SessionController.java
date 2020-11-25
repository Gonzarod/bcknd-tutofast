package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.model.SessionDetail;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.SessionResource;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import com.evertix.tutofastbackend.service.SessionDetailService;
import com.evertix.tutofastbackend.service.SessionService;
import io.cucumber.java.eo.Se;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Session", description = "API is Ready")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionDetailService sessionDetailService;

    @GetMapping("/sessions")
    @Operation(summary = "Get All Sessions", description = "Get all Sessions. Endpoint is public.",
            tags = {"Sessions"},
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
    public Page<SessionResource> getAllSessions(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.sessionService.getAllSessions(pageable);
    }

    @PostMapping("/sessions/courses/{courseId}/students/{studentId}/request")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @Operation(summary = "Create a session request", description = "It allows students to request a session. Endpoint can only be accessed by role student",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public ResponseEntity<?> createSessionRequest(@PathVariable(name = "courseId") Long courseId,
                                                @PathVariable(name = "studentId") Long studentId,
                                                @Valid @RequestBody SessionSaveResource resource){
        return sessionService.createSessionRequest(courseId, studentId, resource);
    }

    @GetMapping("/sessions/student/{studentId}/opens")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @Operation(summary = "Get all students open session request", description = "It allows to fetch opens sessions request (sessions still with no teacher assigned). Endpoint can only be accessed by role student"
            ,security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<Session> getAllOpenSessionRequestsByStudentId(@PathVariable(name = "studentId") Long studentId){
        return sessionService.getAllOpenSessionRequestsByStudentId(studentId);
    }

    @GetMapping("/sessions/student/{studentId}/closed")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all students closed session request", description = "It allows to fetch closed sessions request ("+
                                                                                "classes with assigned teacher but without class being taught). Endpoint can only be accessed by role student"
            ,security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<Session> getAllClosedSessionRequestsByStudentId(@PathVariable(name = "studentId") Long studentId){
        return sessionService.getAllClosedSessionRequestsByStudentId(studentId);
    }

    @GetMapping("/sessions/student/{studentId}/finishedRated")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all students finished and rated sessions", description = "It allows to fetch finished and rated sessions request ("+
            "classes with assigned teacher but without class being taught). Endpoint can only be accessed by role student"
            ,security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<Session> getAllFinishedAndRatedSessionRequestsByStudentId(@PathVariable(name = "studentId") Long studentId){
        return sessionService.getAllFinishedAndRatedSessionRequestsByStudentId(studentId);
    }

    @GetMapping("/sessions/student/{studentId}/finishedNoRated")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all students finished and no rated session request", description = "It allows to fetch finished and no rated session"
            ,security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<Session> getAllFinishedAndNoRatedSessionRequestsByStudentId(@PathVariable(name = "studentId") Long studentId){
        return sessionService.getAllFinishedAndNoRatedSessionRequestsByStudentId(studentId);
    }

    @PostMapping("/sessions/sessionDetail//teacher/{teacherId}/apply")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Apply to open session", description = "It allows teacher to apply to a session",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public ResponseEntity<?> applyToSession(@PathVariable Long sessionId,@PathVariable Long teacherId){
        return sessionService.applyToSession(sessionId,teacherId);
    }

    @GetMapping("/sessions/{sessionId}/detail")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get Session Detail", description = "It fetchs all user get",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<SessionDetail> getSessionDetail(@PathVariable Long sessionId){
        return sessionDetailService.getAllSessionDetailsBySessionId(sessionId);
    }

    @PostMapping("/sessions/sessionDetail/{sessionDetailId}")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Choose Teacher", description = "This endpoint assigns a teacher to teach a session all user get",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public ResponseEntity<?> acceptTeacher(@PathVariable Long sessionDetailId){
        return sessionService.acceptTeacher(sessionDetailId);
    }

    @GetMapping("/sessions/open")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get All Open Request", description = "Get All Open Request. Endpoint can only be accessed by role student and Admin"
            ,security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<Session> getAllOpenSessionRequest(){
        return sessionService.getAllOpenSessionRequest();
    }

    @GetMapping("/sessions/finished")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Finished Sessions", description = "Get All Finished Sessions. Endpoint can be accessed by any role"
            ,security = @SecurityRequirement(name = "bearerAuth"),tags = {"Session"})
    public List<Session> getAllFinishedSessionRequest(){
        return sessionService.getAllFinishedSession();
    }


/*
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


 */

}
