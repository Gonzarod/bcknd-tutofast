package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.TutofastBackendApplication;
import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import com.evertix.tutofastbackend.security.payload.request.LoginRequest;
import com.evertix.tutofastbackend.security.payload.response.JwtResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.net.URL;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TutofastBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SessionUnitTesting {

    private String token;

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/sessions/");
    }

    @Test
    public void createSessionRequest(){

        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        SessionSaveResource resource = new SessionSaveResource(new Date(2020, Calendar.OCTOBER,22,17,0),
                                                              new Date(2020, Calendar.OCTOBER,22,19,0),
                                                              "Segunda Guerra Mundial");

        HttpEntity<?> request = new HttpEntity<>(resource, headers);

        ResponseEntity<Session> responseEntity = template.postForEntity(base.toString()+"courses/"+2+"/students/"+2+"/request",
                                                                    request, Session.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Topic is: "+responseEntity.getBody().getTopic(),"Segunda Guerra Mundial",responseEntity.getBody().getTopic());


    }

    String getAuthenticationJWT(String username,String password){

        try{
            HttpHeaders headers = new HttpHeaders();

            LoginRequest loginRequest = new LoginRequest(username, password);

            HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

            ResponseEntity<JwtResponse> responseEntity = template.postForEntity("http://localhost:" + port + "/api/auth/signin",request, JwtResponse.class);

            return responseEntity.getBody().getToken();

        } catch (Exception e) {
            return null;
        }

    }
/*
    int getCurrentNumberOfElements(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<PlanResource>> responseType = new ParameterizedTypeReference<RestPageImpl<PlanResource>>() { };
        ResponseEntity<RestPageImpl<PlanResource>> responseEntity = template.exchange(base.toString(), HttpMethod.GET,request,responseType);

        return (int) responseEntity.getBody().getTotalElements();

    }*/

}
