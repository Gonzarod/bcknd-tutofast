package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.Resource;
import com.evertix.tutofastbackend.repository.CourseRepository;
import com.evertix.tutofastbackend.repository.ResourceRepository;
import com.evertix.tutofastbackend.repository.SessionRepository;
import com.evertix.tutofastbackend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Page<Resource> getAllResourcesByCourseId(Long courseId, Pageable pageable) {
        return resourceRepository.findAllByCourseId(courseId, pageable);
    }

    @Override
    public Page<Resource> getAllResourcesBySessionId(Long sessionId, Pageable pageable) {
        return resourceRepository.findAllBySessionId(sessionId, pageable);
    }

    @Override
    public Resource createResource(Long sessionId, Long courseId, Resource resource) {
        return sessionRepository.findById(sessionId).map(session -> {
            return courseRepository.findById(courseId).map(course -> {
                resource.setCourse(course);
                resource.setSession(session);
                return resourceRepository.save(resource);
            }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
    }

    @Override
    public Resource updateResource(Long sessionId, Long courseId, Long resourceId, Resource resourceDetails) {
        return sessionRepository.findById(sessionId).map(session -> {
            return courseRepository.findById(courseId).map(course -> {
                return resourceRepository.findById(resourceId).map(resource -> {
                    resource.setCourse(course);
                    resource.setSession(session);
                    resource.setTittle(resourceDetails.getTittle());
                    resource.setDescription(resourceDetails.getDescription());
                    resource.setContent(resourceDetails.getContent());
                    return resourceRepository.save(resource);
                }).orElseThrow(()-> new ResourceNotFoundException("Resource with Id: "+resourceId+" not found"));
            }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
    }

    @Override
    public ResponseEntity<?> deleteResource(Long sessionId, Long courseId, Long resourceId) {
        return sessionRepository.findById(sessionId).map(session -> {
            return courseRepository.findById(courseId).map(course -> {
                return resourceRepository.findById(resourceId).map(resource -> {
                    resourceRepository.delete(resource);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("Resource with Id: "+resourceId+" not found"));
            }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
    }
}
