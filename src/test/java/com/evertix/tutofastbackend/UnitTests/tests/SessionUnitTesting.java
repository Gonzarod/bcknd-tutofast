package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.SessionResource;
import com.evertix.tutofastbackend.resource.SessionSaveResource;
import com.evertix.tutofastbackend.util.RestPageImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionUnitTesting extends TutofastUnitTest {

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
        System.out.println("Todo ok--------------------------->0");
        SessionSaveResource resource = new SessionSaveResource(new Date(2020, Calendar.OCTOBER,22,17,0),
                                                              new Date(2020, Calendar.OCTOBER,22,19,0),
                                                              "Segunda Guerra Mundial");

        System.out.println("Todo ok--------------------------->1");
        HttpEntity<?> request = new HttpEntity<>(resource, headers);
        System.out.println("Todo ok--------------------------->2");
        ResponseEntity<SessionResource> responseEntity = template.postForEntity(base.toString()+"courses/"+2+"/students/"+2+"/request",
                                                                    request, SessionResource.class);
        System.out.println("Todo ok--------------------------->3");
        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        System.out.println("Todo ok--------------------------->4");
        Assert.assertEquals("Topic is: "+responseEntity.getBody().getTopic(),"Segunda Guerra Mundial",responseEntity.getBody().getTopic());
        System.out.println("Todo ok--------------------------->1");


    }

    @Test
    public void getClosedOpenRequest(){
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<SessionResource>> responseType = new ParameterizedTypeReference<List<SessionResource>>() { };
        ResponseEntity<List<SessionResource>> responseEntity = template.exchange(base.toString()+"student/2/closed", HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Size is "+responseEntity.getBody().size(),0,responseEntity.getBody().size());
    }

    @Test
    public void getStudentsOpenRequest(){

    }

    @Test
    public void getStudentsFinishedAndRatedRequest(){

    }

    @Test
    public void getStudentsFinishedAndNonRatedRequest(){

    }


    @Override
    public int getCurrentNumberOfElements() {
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<Session>> responseType = new ParameterizedTypeReference<RestPageImpl<Session>>() { };
        ResponseEntity<RestPageImpl<Session>> responseEntity = template.exchange(base.toString(), HttpMethod.GET,request,responseType);

        return (int) responseEntity.getBody().getTotalElements();
    }
/*
    int getCurrentNumberOfElements(){



    }*/

}
