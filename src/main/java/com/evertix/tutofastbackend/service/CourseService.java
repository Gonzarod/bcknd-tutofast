package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.resource.CourseResource;
import com.evertix.tutofastbackend.resource.CourseSaveResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    //Course name is optional
    List<CourseResource> getAllCourses(String name);
    //Course name is optional
    Page<CourseResource> getAllCoursesPaginated(String name,Pageable pageable);

    CourseResource getCourseById(Long courseId);

    CourseResource createCourse(CourseSaveResource course);
    CourseResource updateCourse(Long courseId, CourseSaveResource courseDetails);
    ResponseEntity<?> deleteCourse(Long courseId);
}
