package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.model.Session;
import com.evertix.tutofastbackend.resource.PlanResource;
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

public class SessionUnitTesting extends UnitTest {

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

    @Test
    public void getStudentsOpenRequest(){
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<PlanResource>> responseType = new ParameterizedTypeReference<RestPageImpl<PlanResource>>() { };
        ResponseEntity<RestPageImpl<PlanResource>> responseEntity = template.exchange(base.toString()+"student/2/closed", HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Size is "+responseEntity.getBody().getTotalElements(),5,responseEntity.getBody().getTotalElements());
    }

    @Test
    public void getStudentsClosedRequest(){

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
