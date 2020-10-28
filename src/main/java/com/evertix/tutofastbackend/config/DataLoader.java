package com.evertix.tutofastbackend.config;

import com.evertix.tutofastbackend.model.*;
import com.evertix.tutofastbackend.repository.*;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;
import com.evertix.tutofastbackend.service.AuthenticationService;
import com.evertix.tutofastbackend.service.SessionService;
import com.evertix.tutofastbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Component
public class DataLoader {

    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionService subscriptionService;
    private final ComplaintRepository complaintRepository;
    private final ReviewRepository reviewRepository;
    private final SessionService sessionService;

    @Autowired
    public DataLoader(RoleRepository roleRepository, CourseRepository courseRepository, AuthenticationService authenticationService,
                      UserRepository userRepository, PlanRepository planRepository, ComplaintRepository complaintRepository, SubscriptionService subscriptionService,
                      ReviewRepository reviewRepository, SessionService sessionService) {

        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.authenticationService=authenticationService;
        this.userRepository=userRepository;
        this.planRepository=planRepository;
        this.subscriptionService=subscriptionService;
        this.complaintRepository=complaintRepository;
        this.reviewRepository=reviewRepository;
        this.sessionService=sessionService;
        loadData();
    }

    private void loadData() {

        this.addRoles();
        this.addCourses();
        this.registerUserAdmin();
        this.registerUserStudent();
        this.registerTeacher();
        this.setTeacherCourses();
        this.addPlans();
        this.subscribeToPlan();
        this.addComplaint();
        this.createSessionRequest();
        this.addReview();

    }

    private void addPlans() {

        this.planRepository.save(new Plan("Free","7 day of trial","You are given 4 hours of free session. You can use them within the next 5 days.",
                                            (short) 4, BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),true));

        this.planRepository.save(new Plan("Basic","30 day","You are given 8 hours of sessions. You can use them in a period of 30 days",
                (short) 8, BigDecimal.valueOf(90.50).setScale(2, RoundingMode.HALF_UP),true));

        this.planRepository.save(new Plan("Platinum","30 day","You are given 12 hours of sessions. You can use them in a period of 30 days",
                (short) 12, BigDecimal.valueOf(140.00).setScale(2, RoundingMode.HALF_UP),true));

        this.planRepository.save(new Plan("Gold","30 day","You are given 20 hours of sessions. You can use them in a period of 30 days",
                (short) 20, BigDecimal.valueOf(170.00).setScale(2, RoundingMode.HALF_UP),true));

        this.planRepository.save(new Plan("Unlimited","Unlimited","You are given unlimited hours of sessions. You can use them in a period of 30 days",
                (short) 30, BigDecimal.valueOf(250.00).setScale(2, RoundingMode.HALF_UP),true));

    }

    void addRoles(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ERole.ROLE_STUDENT));
        roles.add(new Role(ERole.ROLE_TEACHER));
        roles.add(new Role(ERole.ROLE_ADMIN));

        this.roleRepository.saveAll(roles);
    }

    void addCourses(){
        List<Course> courseList = new ArrayList<>();

        //Course 1
        Course course1 = new Course("Spanish", "Spanish");
        Course course2 = new Course("History", "History");
        Course course3 = new Course("Arithmetics", "Arithmetics");
        Course course4 = new Course("Geometry", "Geometry");
        Course course5 = new Course("Geography", "Geography");
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        courseList.add(course5);
        this.courseRepository.saveAll(courseList);

    }

    void registerUserAdmin(){

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        SignUpRequest userAdmin = new SignUpRequest("jose.admin","password","jose@gmail.com",roles,"Jose",
                "Flores","77332217","994093798",LocalDate.now(), "Jr Monte Caoba");
        //this.userRepository.save(userStudent);
        this.authenticationService.registerUser(userAdmin);

    }

    void registerUserStudent(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_STUDENT");
        SignUpRequest userStudent = new SignUpRequest("jesus.student","password","jesus@gmail.com",roles,"Jesus",
                "Duran","77332215","994093796", LocalDate.now(), "Jr Monte Algarrobo");
        //this.userRepository.save(userStudent);
        this.authenticationService.registerUser(userStudent);

    }

    void registerTeacher(){

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_TEACHER");
        SignUpRequest userTeacher = new SignUpRequest("albert.teacher","password","albert@gmail.com",roles,"Albert",
                "Mayta","77332216","994093797",LocalDate.now(), "Jr Monte Cedro");
        //this.userRepository.save(userStudent);
        this.authenticationService.registerUser(userTeacher);

    }

    void setTeacherCourses(){
        List<Course> courses = courseRepository.findAll();
        Optional<User> user = this.userRepository.findByUsername("albert.teacher");
        user.ifPresent(value -> {
            value.setCourses(courses);
            this.userRepository.save(value);
        });
    }


    void subscribeToPlan(){

        Optional<Plan> plan = this.planRepository.findByTitle("Free");
        Optional<User> user = this.userRepository.findByUsername("jesus.student");

        plan.ifPresent(plan1 -> user.ifPresent(user1 -> this.subscriptionService.subscribeToPlan(plan1.getId(),user1.getId())));



    }

    void addComplaint(){

        Optional<User> student = this.userRepository.findByUsername("jesus.student");
        Optional<User> teacher = this.userRepository.findByUsername("albert.teacher");

        Complaint complaint = new Complaint();
        complaint.setReason("Teacher didnt attend to class");
        complaint.setDescription("Teacher Albert didnt attend to class");
        student.ifPresent(student1-> teacher.ifPresent(teacher1->{
            complaint.setMadeBy(student1);
            complaint.setReported(teacher1);
        }));

        this.complaintRepository.save(complaint);

    }

    void addReview(){

        Optional<User> student = this.userRepository.findByUsername("jesus.student");
        Optional<User> teacher = this.userRepository.findByUsername("albert.teacher");

        Review review = new Review();
        review.setDescription("Really good teacher");
        review.setStars((short) 5);
        student.ifPresent(student1-> teacher.ifPresent(teacher1->{
            review.setStudent(student1);
            review.setTeacher(teacher1);
        }));

        this.reviewRepository.save(review);

    }

    void createSessionRequest(){

        Optional<User> student = this.userRepository.findByUsername("jesus.student");
        Optional<Course> course = this.courseRepository.findByName("Spanish");
        SessionSaveResource sessionSaveResource=new SessionSaveResource(new Date(2020, Calendar.OCTOBER,22,17,0),
                new Date(2020, Calendar.OCTOBER,22,19,0),
                "Segunda Guerra Mundial");

        student.ifPresent(student1-> course.ifPresent(course1 -> this.sessionService.createSessionRequest(student1.getId(),course1.getId(), sessionSaveResource)));

    }




}

