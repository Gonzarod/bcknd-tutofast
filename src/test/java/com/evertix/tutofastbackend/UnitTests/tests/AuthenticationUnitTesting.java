package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.resource.UserResource;
import com.evertix.tutofastbackend.security.payload.request.LoginRequest;

import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;
import com.evertix.tutofastbackend.security.payload.response.JwtResponse;
import com.evertix.tutofastbackend.security.payload.response.MessageResponse;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationUnitTesting extends TutofastUnitTest {

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/auth/");
    }

    @Test
    public void RightCredentials_Login(){

        HttpHeaders headers = new HttpHeaders();

        LoginRequest loginRequest = new LoginRequest("jesus.student", "password");

        HttpEntity<?> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<JwtResponse> responseEntity = template.postForEntity(base.toString()+"signin",request, JwtResponse.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),400,responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody().getUsername(),"jesus.student",responseEntity.getBody().getUsername());
    }

    @Test
    public void BadCredentials_Login(){
        HttpHeaders headers = new HttpHeaders();

        LoginRequest loginRequest = new LoginRequest("jesus.student", "password11");

        HttpEntity<?> request = new HttpEntity<>(loginRequest, headers);

        //ResponseEntity<String> result = this.template.postForEntity(base, request, String.class);
        ResponseEntity<MessageResponse> responseEntity = template.postForEntity(base.toString()+"signin",request,MessageResponse.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),400,responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody().getMessage(),"Bad Credentials",responseEntity.getBody().getMessage());
    }

    @Test
    public void Successfully_SignUp(){

        HttpHeaders headers = new HttpHeaders();

        SignUpRequest signUpRequest = this.SetUpNewStudentUser("elkasike.student","password","elkasike@gmail.com","Kasike",
                "Ramos","77332214","994093007", LocalDate.now(), "Montevideo");

        HttpEntity<?> request = new HttpEntity<>(signUpRequest, headers);

        //ResponseEntity<String> result = this.template.postForEntity(base, request, String.class);
        ResponseEntity<UserResource> responseEntity = template.postForEntity(base.toString()+"signup",request,UserResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody().getUsername(),"elkasike.student",responseEntity.getBody().getUsername());
    }

    @Test
    public void UserTaken_FailSignUp(){

        HttpHeaders headers = new HttpHeaders();

        SignUpRequest signUpRequest = this.SetUpNewStudentUser("jesus.student","password","elkasike2@gmail.com","Kasike2",
                                                                "Ramos2","77332212","994093002", LocalDate.now(), "Av Larco");

        HttpEntity<?> request = new HttpEntity<>(signUpRequest, headers);

        //ResponseEntity<String> result = this.template.postForEntity(base, request, String.class);
        ResponseEntity<MessageResponse> responseEntity = template.postForEntity(base.toString()+"signup",request,MessageResponse.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),400,responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody().getMessage(),"Username is already taken!",responseEntity.getBody().getMessage());

    }

    public SignUpRequest SetUpNewStudentUser(String username, String password, String email, String name,
                                             String lastName, String dni, String phone, LocalDate birthday,String address){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_STUDENT");

        SignUpRequest userStudent = new SignUpRequest(username,password,email,roles,name,
                lastName,dni,phone, birthday, address);

        return userStudent;


    }

    @Override
    public int getCurrentNumberOfElements() {
        /*
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<CourseResource>> responseType = new ParameterizedTypeReference<RestPageImpl<CourseResource>>() { };
        ResponseEntity<RestPageImpl<CourseResource>> responseEntity = template.exchange("http://localhost:" + port + "/api/user, HttpMethod.GET,request,responseType);
        */
        //return (int) responseEntity.getBody().getTotalElements();

        return 0;
    }
}
