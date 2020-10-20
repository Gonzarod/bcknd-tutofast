package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.*;
import com.evertix.tutofastbackend.repository.*;
import com.evertix.tutofastbackend.security.payload.response.MessageResponse;
import com.evertix.tutofastbackend.service.SessionDetailService;
import com.evertix.tutofastbackend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SessionDetailRepository sessionDetailRepository;

    @Autowired
    private SessionDetailService sessionDetailService;

    @Override
    public ResponseEntity<?> createSessionRequest(Long courseId, Long studentId, Session session) {
        return userRepository.findById(studentId).map(user -> {
            if(user.getRoles().contains(this.roleRepository.findByName(ERole.ROLE_STUDENT).get())){
                if(this.hasActiveSubscription(user.getId())){
                    if(user.getCreditHours()>=calculateHours(session.getEnd_at(),session.getStart_at())){
                        return courseRepository.findById(courseId).map(course -> {
                            session.setStudent(user);
                            session.setCourse(course);
                            session.setStatus(EStatus.OPEN);
                            user.setCreditHours((short) (user.getCreditHours()-calculateHours(session.getEnd_at(),session.getStart_at())));
                            return ResponseEntity.ok(sessionRepository.save(session));
                        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
                    }else {
                        return ResponseEntity.badRequest().body(new MessageResponse("You dont have enough hours"));
                    }
                }else{
                    return ResponseEntity.badRequest().body(new MessageResponse("Dont have any subscription"));
                }

            }else{
                return ResponseEntity.badRequest().body(new MessageResponse("Only student can request a session"));
            }

        }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+studentId+" not found"));
    }

    @Override
    public Page<Session> getAllOpenSessionRequestsByStudentId(Long studentId,Pageable pageable) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.OPEN,pageable);
    }

    @Override
    public Page<Session> getAllClosedSessionRequestsByStudentId(Long studentId,Pageable pageable) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.CLOSED,pageable);
    }

    @Override
    public Page<Session> getAllFinishedAndRatedSessionRequestsByStudentId(Long studentId,Pageable pageable) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.FINISHED_AND_RATED,pageable);
    }

    @Override
    public Page<Session> getAllFinishedAndNoRatedSessionRequestsByStudentId(Long studentId,Pageable pageable) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.FINISHED_AND_NO_RATED,pageable);
    }

    @Override
    public ResponseEntity<?> applyToSession(Long sessionId, Long teacherId) {
        return sessionRepository.findById(sessionId).map(session->
                userRepository.findById(teacherId).map(user -> {
                    if(user.getRoles().contains(this.roleRepository.findByName(ERole.ROLE_TEACHER).get())){
                        if (session.getStatus().equals(EStatus.OPEN)){
                            return ResponseEntity.ok(
                                    this.sessionDetailService.createSessionDetail(sessionId,teacherId,new SessionDetail(false))
                            );
                        }else{
                            return ResponseEntity.badRequest().body(new MessageResponse("The session request is no longer open"));
                        }

                    }else{
                        return ResponseEntity.badRequest().body(new MessageResponse("Only teachers can apply to a session"));
                    }
                }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+teacherId+" not found")))
                .orElseThrow(()-> new ResourceNotFoundException("User with Id: "+teacherId+" not found"));
    }

    @Override
    public ResponseEntity<?> acceptTeacher(Long sessionDetailId) {
        return this.sessionDetailRepository.findById(sessionDetailId).map(sessionDetail -> {
            if(sessionDetail.getSession().getStatus().equals(EStatus.OPEN)){
                return ResponseEntity.ok(this.sessionDetailService.acceptTeacher(sessionDetailId));
            }else {
                return ResponseEntity.badRequest().body(new MessageResponse("Session is no longer open"));
            }
        }).orElseThrow(()->new ResourceNotFoundException("Session Detail not Found"));
    }


    @Override
    public Page<Session> getAllSessionsByStudentId(Long studentId, Pageable pageable) {
        return sessionRepository.getAllByStudentId(studentId, pageable);
    }

    @Override
    public Page<Session> getAllSessionsByCourseName(String courseName, Pageable pageable) {
        return sessionRepository.getAllByCourseName(courseName, pageable);
    }
/*
    @Override
    public Page<Session> getAllSessionsByStatus(String status, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Session> getAllSessionsByStatus(EStatus status, Pageable pageable) {
        return sessionRepository.getAllByStatus(status, pageable);
    }
    */



    @Override
    public Session createSession(Long courseId, Long studentId, Session session) {
        return courseRepository.findById(courseId).map(course -> {
            return userRepository.findById(studentId).map(user -> {
                session.setStudent(user);
                session.setCourse(course);
                return sessionRepository.save(session);
            }).orElseThrow(()-> new ResourceNotFoundException("Student with Id: "+studentId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
    }

    @Override
    public Session updateSession(Long courseId, Long studentId, Long sessionId, Session sessionDetails) {
        return courseRepository.findById(courseId).map(course -> {
            return userRepository.findById(studentId).map(user -> {
                return sessionRepository.findById(sessionId).map(session -> {
                    session.setStudent(user);
                    session.setCourse(course);
                    session.setStatus(sessionDetails.getStatus());
                    session.setTopic(sessionDetails.getTopic());
                    session.setLink(sessionDetails.getLink());
                    return sessionRepository.save(session);
                }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
            }).orElseThrow(()-> new ResourceNotFoundException("Student with Id: "+studentId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
    }

    @Override
    public ResponseEntity<?> deleteSession(Long courseId, Long studentId, Long sessionId) {
        return courseRepository.findById(courseId).map(course -> {
            return userRepository.findById(studentId).map(user -> {
                return sessionRepository.findById(sessionId).map(session -> {
                    sessionRepository.delete(session);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("Session with Id: "+sessionId+" not found"));
            }).orElseThrow(()-> new ResourceNotFoundException("Student with Id: "+studentId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
    }

    Short calculateHours(Date date1, Date date2){
        long difference=  (date1.getTime()-date2.getTime())/(60 * 60 * 1000);

        return (short) difference;
        //return ;
    }

    Boolean hasActiveSubscription(Long userId){
        List<Subscription> userSubsHistory=this.subscriptionRepository.findAllByUserId(userId);
        if(userSubsHistory.size()>0){
            boolean hasSub=false;
            for(Subscription sub: userSubsHistory){
                if (sub.getActive()){
                    hasSub=true;
                }
            }
            return hasSub;
        }else {
            return false;
        }
    }
}
