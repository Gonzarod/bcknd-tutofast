package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Course;
import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.model.User;
import com.evertix.tutofastbackend.resource.CourseResource;
import com.evertix.tutofastbackend.resource.CourseSaveResource;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.UserResource;
import com.evertix.tutofastbackend.service.CourseService;
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

@Tag(name = "Course", description = "API is Ready")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses", description = "Get All Courses", tags = {"Course"},
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
    public Page<CourseResource> getAllCourses(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Course> coursePage=courseService.getAllCourses(pageable);
        List<CourseResource> courses = coursePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(courses,pageable,coursePage.getTotalElements());
    }

    @GetMapping("/courses/{name}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses by Course Name", description = "Get All Courses by Course Name. Only Access for Authenticated Users", tags = {"Course"},
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
    public Page<CourseResource> getAllCoursesByName(@PathVariable String name,@PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Course> coursePage = this.courseService.getCoursesByName(name,pageable);
        List<CourseResource> resources = coursePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, coursePage.getTotalElements());
    }
    //
    @GetMapping("/courses/{courseId}/teachers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get All Course Teachers", description = "Get All the teachers that teach an specific course. Only Access for Authenticated Users", tags = {"Course"},
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
        List<UserResource> userList = this.courseService.getAllTeachersOfOneCourse(courseId).stream().map(user -> mapper.map(user,UserResource.class)).collect(Collectors.toList());
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @PostMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Post Course", description = "Create Course",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"Course"})
    public CourseResource createCourse(@Valid @RequestBody CourseSaveResource resource){
        return convertToResource(courseService.createCourse(convertToEntity(resource)));
    }

    @PutMapping("/courses/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Put Course", description = "Update Course",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"Course"})
    public CourseResource updateCourse(@PathVariable(name = "courseId") Long courseId,
                                       @Valid @RequestBody CourseSaveResource resource){
        return convertToResource(courseService.updateCourse(courseId, convertToEntity(resource)));
    }

    @DeleteMapping("/courses/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete Course", description = "Delete Course",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"Course"})
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "courseId") Long courseId){
        return courseService.deleteCourse(courseId);
    }

    private Course convertToEntity(CourseSaveResource resource){return mapper.map(resource, Course.class);}

    private CourseResource convertToResource(Course entity){return mapper.map(entity, CourseResource.class);}
}
