package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Course;
import com.evertix.tutofastbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    Page<Course> getAllCourses(Pageable pageable);
    Course getCourseById(Long courseId);
    Page<Course> getCoursesByName(String courseName,Pageable pageable);

    Course createCourse(Course course);
    Course updateCourse(Long courseId, Course courseDetails);
    ResponseEntity<?> deleteCourse(Long courseId);
}
