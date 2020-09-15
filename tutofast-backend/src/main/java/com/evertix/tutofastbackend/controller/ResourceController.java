package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Resource;
import com.evertix.tutofastbackend.resource.ResourceResource;
import com.evertix.tutofastbackend.resource.ResourceSaveResource;
import com.evertix.tutofastbackend.service.ResourceService;
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

@Tag(name = "Resource", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/courses/{courseId}/resources")
    @Operation(summary = "Get All Resources By Course", description = "Get All Resources By Course", tags = {"Resource"},
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
    public Page<ResourceResource> getAllResourcesByCourseId(@PathVariable(name = "courseId") Long courseId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Resource> resourcePage = resourceService.getAllResourcesByCourseId(courseId, pageable);
        List<ResourceResource> resources = resourcePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resourcePage.getTotalElements());
    }

    @GetMapping("/sessions/{sessionId}/resources")
    @Operation(summary = "Get All Resources By Course", description = "Get All Resources By Course", tags = {"Resource"},
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
    public Page<ResourceResource> getAllResourcesBySessionId(@PathVariable(name = "sessionId") Long sessionId, @PageableDefault @Parameter(hidden = true)  Pageable pageable){
        Page<Resource> resourcePage = resourceService.getAllResourcesBySessionId(sessionId, pageable);
        List<ResourceResource> resources = resourcePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resourcePage.getTotalElements());
    }

    @PostMapping("/sessions/{sessionId}/courses/{courseId}/resources")
    @Operation(summary = "Post Resource", description = "Create Resource", tags = {"Resource"})
    public ResourceResource createResource(@PathVariable(name = "sessionId") Long sessionId,
                                           @PathVariable(name = "courseId") Long courseId,
                                           @Valid @RequestBody ResourceSaveResource resource){
        return convertToResource(resourceService.createResource(sessionId,courseId,convertToEntity(resource)));
    }

    @PutMapping("/sessions/{sessionId}/courses/{courseId}/resources/{resourceId}")
    @Operation(summary = "Put Resource", description = "Update Resource", tags = {"Resource"})
    public ResourceResource updateResource(@PathVariable(name = "sessionId") Long sessionId,
                                           @PathVariable(name = "courseId") Long courseId,
                                           @PathVariable(name = "resourceId") Long resourceId,
                                           @Valid @RequestBody ResourceSaveResource resource){
        return convertToResource(resourceService.updateResource(sessionId,courseId,resourceId,convertToEntity(resource)));
    }

    @DeleteMapping("/sessions/{sessionId}/courses/{courseId}/resources/{resourceId}")
    @Operation(summary = "Delete Resource", description = "Delete Resource", tags = {"Resource"})
    public ResponseEntity<?> deleteResource(@PathVariable(name = "sessionId") Long sessionId,
                                            @PathVariable(name = "courseId") Long courseId,
                                            @PathVariable(name = "resourceId") Long resourceId){
        return resourceService.deleteResource(sessionId, courseId, resourceId);
    }

    private Resource convertToEntity(ResourceSaveResource resource){return mapper.map(resource, Resource.class);}
    private ResourceResource convertToResource(Resource entity){return mapper.map(entity, ResourceResource.class);}
}
