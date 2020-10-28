package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.resource.CourseResource;
import com.evertix.tutofastbackend.resource.CourseSaveResource;
import com.evertix.tutofastbackend.service.CourseService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Course", description = "API is Ready")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses/page")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses", description = "Get All Courses. Can filter by name (param optional).Endpoint can be accessed by any role", tags = {"Course"},
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
    public Page<CourseResource> getAllCoursesPaginated(@RequestParam(required = false) @Parameter(description = "is Optional") String name,
                                                       @PageableDefault @Parameter(hidden = true) Pageable pageable){
        return this.courseService.getAllCoursesPaginated(name,pageable);
    }

    @GetMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses", description = "Get All Courses. Can filter by name (param optional).Endpoint can be accessed by any role", tags = {"Course"}
            ,security = @SecurityRequirement(name = "bearerAuth"))
    public List<CourseResource> getAllCourses(@RequestParam(required = false) @Parameter(description = "is Optional") String name){
        System.out.println(name);
        return this.courseService.getAllCourses(name);
    }

    @GetMapping("/courses/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get Plan by Id", description = "Get Course by Id. Endpoint can be accessed by any role.",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Plan"})
    public CourseResource getCourseById(@PathVariable Long courseId){
        return this.courseService.getCourseById(courseId);
    }

    //
    @PostMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Post Course", description = "Create Course. Endpoint can be accessed by any role.",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"Course"})
    public CourseResource createCourse(@Valid @RequestBody CourseSaveResource newCourse){
        return this.courseService.createCourse(newCourse);
    }

    @PutMapping("/courses/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Put Course", description = "Update Course. Endpoint can be accessed by any role.",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"Course"})
    public CourseResource updateCourse(@PathVariable(name = "courseId") Long courseId,
                                       @Valid @RequestBody CourseSaveResource courseDetails){
        return this.courseService.updateCourse(courseId,courseDetails);
    }

    @DeleteMapping("/courses/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete Course", description = "Delete Course. Endpoint can be accessed by any role.",
               security = @SecurityRequirement(name = "bearerAuth"), tags = {"Course"})
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "courseId") Long courseId){
        return courseService.deleteCourse(courseId);
    }

}
