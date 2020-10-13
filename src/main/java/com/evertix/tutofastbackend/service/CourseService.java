package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    Page<Course> getAllCourses(Pageable pageable);
    Course getCourseByName(String courseName);
    Course createCourse(Course course);
    Course updateCourse(Long courseId, Course courseDetails);
    ResponseEntity<?> deleteCourse(Long courseId);
}
