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

@Tag(name = "User", description = "API is Ready")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Users", description = "Get all Users. Endpoint needs authentication.",
            tags = {"User"},
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
    public Page<UserResource> getAllUsers(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.userService.getAllUsers(pageable);
    }

    @GetMapping("/users/students")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Users of Role Students", description = "Get all Users of Role Students. Endpoint needs authentication.",
            tags = {"User"},
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
    public Page<UserResource> getAllUsersStudents(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.userService.getAllUsersStudents(pageable);
    }

    @GetMapping("/users/username/{username}/role")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get Role by Username. Endpoint is public", description = "Get Role by Username. Endpoint is public",
            tags = {"User"})
    //,security = @SecurityRequirement(name = "bearerAuth")
    public String getAllUsersStudents(@PathVariable String username){
        return this.userService.getRoleByUsername(username);
    }

    @GetMapping("/users/teachers")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Users of Role Teacher", description = "Get all Users of role Teachers. Endpoint needs authentication.",
            tags = {"User"},
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
    public Page<UserResource> getAllUsersTeachers(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.userService.getAllUsersTeachers(pageable);
    }


    @GetMapping("/users/usernameExists/{username}")
    @Operation(summary = "Check username", description = "Check if username is already used by other user. True if is taken, false if not. Endpoint is public.", tags = {"User"})
    public Boolean userExistByUsername(@PathVariable String username){
        return this.userService.userExistsByUsername(username);
    }

    @GetMapping("/users/emailExists/{email}")
    @Operation(summary = "Check email", description = "Check if email is already used by other user.True if is taken, false if not. Endpoint is public.", tags = {"User"})
    public Boolean userExistByEmail(@PathVariable String email){
        return this.userService.userExistsByEmail(email);
    }

    @PutMapping("users/teacher/{userId}/setLinkedin/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Set Linkedin", description = "Allows to link Linkedin profile to your Tutofast account. Endpoint can only be accessed by role teacher and admin.",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public UserResource setLinkedinProfile(@PathVariable Long userId, @RequestBody String linkedin){
        return convertToResource(this.userService.setLinkedinProfile(userId,linkedin));
    }


    @PutMapping("users/teacher/{userId}/activate/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Activate Teacher", description = "When a teacher register, he must be accepted by admin. When teacher is accepted then he can use the full app." +
            "                                                Endpoint can only be accessed by admin.",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public ResponseEntity<?> activateTeacher(@PathVariable Long userId){
        return this.userService.activateTeacher(userId);
    }

    @PutMapping("users/teacher/{userId}/addCourses/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add Courses", description = "Allows teacher to indicate the list courses that he teaches. Endpoint can only be accessed by role teacher and admin.",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public ResponseEntity<?> addCourses(@PathVariable Long userId, @RequestBody List<Long> coursesId){
        return this.userService.addCourses(userId,coursesId);
    }

    @PutMapping("users/teacher/{userId}/removeCourses/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Remove Courses", description = "Allows to remove a list courses. Endpoint can only be accessed by role teacher and admin.",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public ResponseEntity<?> removeCourses(@PathVariable Long userId, @RequestBody List<Long> coursesId){
        return this.userService.removeCourses(userId,coursesId);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get User By Id", description = "View User By Id. Endpoint can be accessed by any role.",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public UserResource getUserById(@PathVariable(name = "userId") Long userId){
        return convertToResource(userService.getUserById(userId));
    }

    @GetMapping("/users/username/{username}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get User By Id", description = "View User By Id. Endpoint can be accessed by any role.",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public UserResource getUserByUsername(@PathVariable String username){
        return convertToResource(userService.getUserByUsername(username));
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Put User", description = "Update User. Endpoint can be accessed by any role.",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public UserResource updateUser(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody UserSaveResource resource){
        return convertToResource(userService.updateUser(userId, convertToEntity(resource)));
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete User", description = "Delete User. Endpoint can only be accessed by role admin.",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") Long userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("users/courses/{courseId}/teachers")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Course Teachers", description = "Get All the teachers that teach an specific course. Endpoint can be accessed by any role.", tags = {"Course"},
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
    public Page<UserResource> getAllTeachersOfOneCourse(@PathVariable Long courseId,@PageableDefault @Parameter(hidden = true) Pageable pageable){
        List<UserResource> userList = this.userService.getAllTeachersOfOneCourse(courseId).stream().map(user -> mapper.map(user,UserResource.class)).collect(Collectors.toList());
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @DeleteMapping("/users/{userId}/baned")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Ban User", description = "Admin can ban user",
               security = @SecurityRequirement(name = "bearerAuth"),tags = {"User"})
    public ResponseEntity<?> banUser(@PathVariable(name = "userId") Long userId){
        return userService.banUser(userId);
    }

    private User convertToEntity(UserSaveResource resource){return mapper.map(resource, User.class);}

    private UserResource convertToResource(User entity){return mapper.map(entity, UserResource.class);}

}
