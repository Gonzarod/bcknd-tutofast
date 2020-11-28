package com.evertix.tutofastbackend.repository;

import com.evertix.tutofastbackend.model.EStatus;
import com.evertix.tutofastbackend.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> getAllByStudentIdAndStatusEquals(Long studentId, EStatus status);
    List<Session> getAllByStatusEquals(EStatus status);
    //List<Session> getAllByStatusEqualsAndCourseId(EStatus status,Long CourseId);
    List<Session> getAllByCourseIdAndStatusEquals(Long courseId,EStatus status);
    Page<Session> getAllByStudentId(Long studentId, Pageable pageable);
    Page<Session> getAllByCourseName(String courseName, Pageable pageable);
    //Page<Session> getAllByStatus(EStatus status, Pageable pageable);
}
