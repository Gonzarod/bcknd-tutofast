package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Course;
import com.evertix.tutofastbackend.model.User;
import com.evertix.tutofastbackend.repository.CourseRepository;
import com.evertix.tutofastbackend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> getAllCourses(Pageable pageable) { return courseRepository.findAll(pageable); }

    @Override
    public Course getCourseById(Long courseId) {
        return this.courseRepository.findById(courseId).orElseThrow(()->
                new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
    }

    @Override
    public Page<Course> getCoursesByName(String courseName, Pageable pageable) {
        return this.courseRepository.findByNameContaining(courseName,pageable);
    }


    @Override
    public Course createCourse(Course course) { return courseRepository.save(course); }

    @Override
    public Course updateCourse(Long courseId, Course courseDetails) {
        return courseRepository.findById(courseId).map(course -> {
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            return courseRepository.save(course);
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+ " not found"));
    }

    @Override
    public ResponseEntity<?> deleteCourse(Long courseId) {
        return courseRepository.findById(courseId).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+ " not found"));
    }
}
