package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Rol;
import com.evertix.tutofastbackend.resource.RolResource;
import com.evertix.tutofastbackend.resource.RolSaveResource;
import com.evertix.tutofastbackend.service.RolService;
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

@Tag(name = "Rol", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RolController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RolService rolService;

    @GetMapping("/roles")
    @Operation(summary = "Get All Roles", description = "Get All Roles", tags = {"Rol"},
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
    public Page<RolResource> getAllRoles(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        List<RolResource> roles = rolService.getAllRoles(pageable).getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int rolesCount = roles.size();
        return new PageImpl<>(roles,pageable,rolesCount);
    }

    @GetMapping("/roles/{rolId}")
    @Operation(summary = "Get Rol By Id", description = "View Rol By Id", tags = {"Rol"})
    public RolResource getRoleById(@PathVariable(name = "rolId") Long rolId){
        return convertToResource(rolService.getRolById(rolId));
    }

    @PostMapping("/roles")
    @Operation(summary = "Post Rol", description = "Create Rol", tags = {"Rol"})
    public RolResource createRole(@Valid @RequestBody RolSaveResource resource){
        return convertToResource(rolService.createRol(convertToEntity(resource)));
    }

    @PutMapping("/roles/{rolId}")
    @Operation(summary = "Put Rol", description = "Update Rol", tags = {"Rol"})
    public RolResource updateRol(@PathVariable(name = "rolId") Long rolId,
                                 @Valid @RequestBody RolSaveResource resource){
        return convertToResource(rolService.updateRol(rolId, convertToEntity(resource)));
    }

    @DeleteMapping("/roles/{rolId}")
    @Operation(summary = "Delete Rol", description = "Delete Rol", tags = {"Rol"})
    public ResponseEntity<?> deleteRol(@PathVariable(name = "rolId") Long rolId){
        return rolService.deleteRol(rolId);
    }

    private Rol convertToEntity(RolSaveResource resource){return mapper.map(resource, Rol.class);}
    private RolResource convertToResource(Rol entity){return mapper.map(entity, RolResource.class);}
}

