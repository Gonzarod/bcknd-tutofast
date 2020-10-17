package com.evertix.tutofastbackend.config;

import com.evertix.tutofastbackend.model.*;
import com.evertix.tutofastbackend.repository.CourseRepository;
import com.evertix.tutofastbackend.repository.RoleRepository;
import com.evertix.tutofastbackend.repository.UserRepository;
import com.evertix.tutofastbackend.repository.WorkExperienceRepository;
import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;
import com.evertix.tutofastbackend.service.AuthenticationService;
import com.evertix.tutofastbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataLoader {

    private RoleRepository roleRepository;
    private WorkExperienceRepository workExperienceRepository;
    private CourseRepository courseRepository;
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    @Autowired
    public DataLoader(RoleRepository roleRepository,
                      CourseRepository courseRepository, UserRepository userRepository, AuthenticationService authenticationService) {

        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.authenticationService=authenticationService;
        this.userRepository=userRepository;
        LoadData();
    }

    private void LoadData() {

        this.loadRoles();
        this.addCourses();
        this.registerUserAdmin();
        this.registerUserStudent();
        this.registerTeacher();
        this.setTeacherCourses();
    }

    void loadRoles(){
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role(ERole.ROLE_STUDENT));
        roles.add(new Role(ERole.ROLE_TEACHER));
        roles.add(new Role(ERole.ROLE_ADMIN));

        this.roleRepository.saveAll(roles);
    }

    void addCourses(){
        List<Course> courseList = new ArrayList<Course>();

        //Course 1
        Course course1 = new Course("Spanish", "Spanish");
        Course course2 = new Course("History", "History");
        Course course3 = new Course("Arithmetics", "Arithmetics");
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
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
        user.get().setCourses(courses);
        this.userRepository.save(user.get());
    }




}
