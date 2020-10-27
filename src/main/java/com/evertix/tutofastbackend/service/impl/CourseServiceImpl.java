package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Course;
import com.evertix.tutofastbackend.repository.CourseRepository;
import com.evertix.tutofastbackend.resource.CourseResource;
import com.evertix.tutofastbackend.resource.CourseSaveResource;
import com.evertix.tutofastbackend.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseResource> getAllCourses(String name) {
        List<Course> courses;
        //Course name is optional
        if (name==null){
            courses=this.courseRepository.findAll();
        }else{
            courses=this.courseRepository.findByNameContaining(name);
        }
        return courses.stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Override
    public Page<CourseResource> getAllCoursesPaginated(String name,Pageable pageable) {
        Page<Course> coursePage;
        if (name == null) {
            coursePage = this.courseRepository.findAll(pageable);
        } else {
            coursePage = this.courseRepository.findByNameContaining(name,pageable);
        }

        List<CourseResource> courses = coursePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(courses, pageable, coursePage.getTotalElements());
    }

    @Override
    public CourseResource getCourseById(Long courseId) {
        return this.courseRepository.findById(courseId).map(this::convertToResource).orElseThrow(()->
                new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
    }

    @Override
    public CourseResource createCourse(CourseSaveResource course) {
        return convertToResource(courseRepository.save(convertToEntity(course)));
    }

    @Override
    public CourseResource updateCourse(Long courseId, CourseSaveResource courseDetails) {
        return courseRepository.findById(courseId).map(course -> {
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            return convertToResource(courseRepository.save(course));
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+ " not found"));
    }

    @Override
    public ResponseEntity<?> deleteCourse(Long courseId) {
        return courseRepository.findById(courseId).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+ " not found"));
    }

    private Course convertToEntity(CourseSaveResource resource){return mapper.map(resource, Course.class);}

    private CourseResource convertToResource(Course entity){return mapper.map(entity, CourseResource.class);}



}
