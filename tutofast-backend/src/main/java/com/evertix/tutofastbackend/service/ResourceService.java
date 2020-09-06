package com.evertix.tutofastbackend.service;

import com.evertix.tutofastbackend.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ResourceService {
    Page<Resource> getAllResourcesByCourseId(Long courseId, Pageable pageable);
    Page<Resource> getAllResourcesBySessionId(Long sessionId, Pageable pageable);
    Resource createResource(Long sessionId, Long courseId, Resource resource);
    Resource updateResource(Long sessionId, Long courseId, Long resourceId, Resource resourceDetails);
    ResponseEntity<?> deleteResource(Long sessionId, Long courseId, Long resourceId);
}
