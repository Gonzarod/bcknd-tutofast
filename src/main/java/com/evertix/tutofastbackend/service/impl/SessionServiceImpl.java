package com.evertix.tutofastbackend.service.impl;

import com.evertix.tutofastbackend.exception.ResourceNotFoundException;
import com.evertix.tutofastbackend.model.*;
import com.evertix.tutofastbackend.repository.*;
import com.evertix.tutofastbackend.resource.SessionResource;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import com.evertix.tutofastbackend.security.payload.response.MessageResponse;
import com.evertix.tutofastbackend.service.SessionDetailService;
import com.evertix.tutofastbackend.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private ModelMapper mapper;

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
    public Page<SessionResource> getAllSessions(Pageable pageable) {
        Page<Session> sessionsPage = this.sessionRepository.findAll(pageable);
        List<SessionResource> resources = sessionsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, sessionsPage.getTotalElements());
    }

    @Override
    public ResponseEntity<?> createSessionRequest(Long courseId, Long studentId, SessionSaveResource sessionDetails) {

        Session session=this.convertToEntity(sessionDetails);

        return userRepository.findById(studentId).map(user -> {
            if(this.hasActiveSubscription(user.getId())){
                if(user.getCreditHours()>=calculateHoursDifference(session.getStart_at(),session.getEnd_at())){
                    return courseRepository.findById(courseId).map(course -> {
                        session.setStudent(user);
                        session.setCourse(course);
                        session.setStatus(EStatus.OPEN);
                        user.setCreditHours((short) (user.getCreditHours()-calculateHoursDifference(session.getStart_at(),session.getEnd_at())));
                        userRepository.save(user);
                        return ResponseEntity.ok(convertToResource(sessionRepository.save(session)));
                    }).orElseThrow(()-> new ResourceNotFoundException("Course with Id: "+courseId+" not found"));
                }else {
                    return ResponseEntity.badRequest().body(new MessageResponse("You dont have enough hours"));
                }
            }else{
                return ResponseEntity.badRequest().body(new MessageResponse("Dont have any subscription"));
            }

        }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+studentId+" not found"));
    }

    @Override
    public List<Session> getAllOpenSessionRequest() {
        return this.sessionRepository.getAllByStatusEquals(EStatus.OPEN);
    }

    @Override
    public List<Session> getAllFinishedSession() {
        List<Session> sessionsFinishedAndRated =this.sessionRepository.getAllByStatusEquals(EStatus.FINISHED_AND_RATED);
        List<Session> sessionsFinishedAndNotRated = this.sessionRepository.getAllByStatusEquals(EStatus.FINISHED_AND_NO_RATED);

        return Stream.concat(sessionsFinishedAndNotRated.stream(),sessionsFinishedAndRated.stream()).collect(Collectors.toList());

    }

    @Override
    public List<Session> getAllOpenSessionRequestsByStudentId(Long studentId) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.OPEN);
    }

    @Override
    public List<Session> getAllClosedSessionRequestsByStudentId(Long studentId) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.CLOSED);
    }

    @Override
    public List<Session> getAllFinishedAndRatedSessionRequestsByStudentId(Long studentId) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.FINISHED_AND_RATED);
    }

    @Override
    public List<Session> getAllFinishedAndNoRatedSessionRequestsByStudentId(Long studentId) {
        return this.sessionRepository.getAllByStudentIdAndStatusEquals(studentId,EStatus.FINISHED_AND_NO_RATED);
    }

    @Override
    public ResponseEntity<?> applyToSession(Long sessionId, Long teacherId) {

        return sessionRepository.findById(sessionId).map(session -> {
            return userRepository.findById(teacherId).map(user -> {
                if (session.getStatus().equals(EStatus.OPEN)){
                    return ResponseEntity.ok(
                            this.sessionDetailService.createSessionDetail(sessionId,teacherId,new SessionDetail(false))
                    );
                }else{
                    return ResponseEntity.badRequest().body(new MessageResponse("The session request is no longer open"));
                }
            }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+teacherId+" not found"));
        }).orElseThrow(()-> new ResourceNotFoundException("User with Id: "+teacherId+" not found"));

    }

    @Override
    public ResponseEntity<?> acceptTeacher(Long sessionDetailId) {
        return this.sessionDetailRepository.findById(sessionDetailId).map(sessionDetail -> {
            if(sessionDetail.getSession().getStatus().equals(EStatus.OPEN)){
                sessionDetail.getSession().setStatus(EStatus.CLOSED);
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

    Short calculateHoursDifference(LocalDateTime fromDate, LocalDateTime toDate){
        long hours = ChronoUnit.HOURS.between(fromDate,toDate);
        return (short) hours;
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

    private Session convertToEntity(SessionSaveResource resource){return mapper.map(resource, Session.class);}
    private SessionResource convertToResource(Session entity){return mapper.map(entity, SessionResource.class);}
}
