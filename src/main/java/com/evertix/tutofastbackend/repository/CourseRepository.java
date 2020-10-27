package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);
    List<Course> findByNameContaining(String name);
    Page<Course> findByNameContaining(String name, Pageable pageable);
    Page<Course> findAllByTeachersId(Long teacherId, Pageable pageable);
}
