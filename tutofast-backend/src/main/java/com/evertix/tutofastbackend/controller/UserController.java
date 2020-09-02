package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.User;
import com.evertix.tutofastbackend.resource.UserResource;
import com.evertix.tutofastbackend.resource.UserSaveResource;
import com.evertix.tutofastbackend.service.UserService;
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

@Tag(name = "User", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/roles/{rolId}/users")
    @Operation(summary = "Get All Users By Rol", description = "Get All Users By Rol", tags = {"User"},
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
    public Page<UserResource> getAllUsersByRolId(@PathVariable(name = "rolId") Long rolId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<User> userPage = userService.getAllUsersByRolId(rolId, pageable);
        List<UserResource> resources = userPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get User By Id", description = "View User By Id", tags = {"User"})
    public UserResource getUserById(@PathVariable(name = "userId") Long userId){
        return convertToResource(userService.getUserById(userId));
    }

    @PostMapping("/roles/{rolId}/users")
    @Operation(summary = "Post User", description = "Create User", tags = {"User"})
    public UserResource createUser(@PathVariable(name = "rolId") Long rolId,
                                   @Valid @RequestBody UserSaveResource resource){
        return convertToResource(userService.createUser(rolId, convertToEntity(resource)));
    }

    @PutMapping("/roles/{rolId}/users/{userId}")
    @Operation(summary = "Put User", description = "Update User", tags = {"User"})
    public UserResource updateUser(@PathVariable(name = "rolId") Long rolId,
                                   @PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody UserSaveResource resource){
        return convertToResource(userService.updateUser(rolId, userId, convertToEntity(resource)));
    }

    @DeleteMapping("/roles/{rolId}/users/{userId}")
    @Operation(summary = "Delete User", description = "Delete User", tags = {"User"})
    public ResponseEntity<?> deleteUser(@PathVariable(name = "rolId") Long rolId,
                                        @PathVariable(name = "userId") Long userId){
        return userService.deleteUser(rolId, userId);
    }

    private User convertToEntity(UserSaveResource resource){return mapper.map(resource, User.class);}
    private UserResource convertToResource(User entity){return mapper.map(entity, UserResource.class);}
}
