package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.WorkExperience;
import com.evertix.tutofastbackend.resource.WorkExperienceResource;
import com.evertix.tutofastbackend.resource.WorkExperienceSaveResource;
import com.evertix.tutofastbackend.service.WorkExperienceService;
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

@Tag(name = "WorkExperience", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkExperienceController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WorkExperienceService workExperienceService;

    @GetMapping("/users/{userId}/workExperiences")
    @Operation(summary = "Get All WorkExperiences By User", description = "Get All WorkExperiences By User", tags = {"WorkExperience"},
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
    public Page<WorkExperienceResource> getAllWorkExperiencesByUserId(@PathVariable(name = "userId") Long userId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<WorkExperience> workExperiencePage = workExperienceService.getAllWorkExperiencesByUserId(userId, pageable);
        List<WorkExperienceResource> resources = workExperiencePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @PostMapping("/users/{userId}/workExperiences")
    @Operation(summary = "Post WorkExperience", description = "Create WorkExperience", tags = {"WorkExperience"})
    public WorkExperienceResource createWorkExperience(@PathVariable(name = "userId") Long userId, @Valid @RequestBody WorkExperienceSaveResource resource){
        return convertToResource(workExperienceService.createWorkExperience(userId, convertToEntity(resource)));
    }

    @PutMapping("/users/{userId}/workExperiences/{workExperienceId}")
    @Operation(summary = "Put WorkExperience", description = "Update WorkExperience", tags = {"WorkExperience"})
    public WorkExperienceResource updateWorkExperience(@PathVariable(name = "userId") Long userId,
                                                       @PathVariable(name = "workExperienceId") Long workExperienceId,
                                                       @Valid @RequestBody WorkExperienceSaveResource resource){
        return convertToResource(workExperienceService.updateWorkExperience(userId, workExperienceId, convertToEntity(resource)));
    }

    @DeleteMapping("/users/{userId}/workExperiences/{workExperienceId}")
    @Operation(summary = "Delete WorkExperience", description = "Delete WorkExperience", tags = {"WorkExperience"})
    public ResponseEntity<?> deleteWorkExperience(@PathVariable(name = "userId") Long userId,
                                                  @PathVariable(name = "workExperienceId") Long workExperienceId){
        return workExperienceService.deleteWorkExperience(userId, workExperienceId);
    }

    private WorkExperience convertToEntity(WorkExperienceSaveResource resource){return mapper.map(resource, WorkExperience.class);}
    private WorkExperienceResource convertToResource(WorkExperience entity){return mapper.map(entity, WorkExperienceResource.class);}
}
