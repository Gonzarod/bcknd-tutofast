package com.evertix.tutofastbackend.UnitTests;

import com.evertix.tutofastbackend.TutofastBackendApplication;
import com.evertix.tutofastbackend.UnitTests.util.RestPageImpl;
//import com.evertix.tutofastbackend.exception.ExceptionResponse;
import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import com.evertix.tutofastbackend.security.payload.request.LoginRequest;

import com.evertix.tutofastbackend.security.payload.response.JwtResponse;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TutofastBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlanUnitTesting {

    private String token;

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/plans/");
    }

    @Test
    public void GetAllPlans(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<PlanResource>> responseType = new ParameterizedTypeReference<RestPageImpl<PlanResource>>() { };
        ResponseEntity<RestPageImpl<PlanResource>> responseEntity = template.exchange(base.toString(), HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Size is "+responseEntity.getBody().getTotalElements(),5,responseEntity.getBody().getTotalElements());

    }

    @Test
    public void GetPlanById(){
        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<PlanResource> responseEntity = template.exchange(base.toString()+"2", HttpMethod.GET,request,PlanResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Plan title is "+responseEntity.getBody().getTitle(),"Basic",responseEntity.getBody().getTitle());

    }
/*
    @Test
    public void GetPlanById_NotFound(){
        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<ExceptionResponse> responseEntity = template.exchange(base.toString()+"50", HttpMethod.GET,request,ExceptionResponse.class);

        Assert.assertEquals("Error Code is: "+responseEntity.getBody().getErrorCode(),"NOT_FOUND",responseEntity.getBody().getErrorCode());
        Assert.assertEquals("Error Message is "+responseEntity.getBody().getErrorMessage(),"Plan with Id: 50 not found",responseEntity.getBody().getErrorMessage());

    }
*/
    @Test
    public void createPlan(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        Plan plan = new Plan("TestPlan","Test","Test",
                (short) 4, BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP));

        HttpEntity<?> request = new HttpEntity<>(plan, headers);

        ResponseEntity<Plan> responseEntity = template.postForEntity(base.toString(),request, Plan.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Plan title is "+responseEntity.getBody().getTitle(),"TestPlan",responseEntity.getBody().getTitle());


    }

    @Test
    public void updatePlan(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        PlanSaveResource plan = new PlanSaveResource("Free Free","7 day of trial","You are given 4 hours of free session. You can use them within the next 5 days.",
                (short) 4, BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP));

        HttpEntity<?> request = new HttpEntity<>(plan, headers);

        ResponseEntity<Plan> responseEntity = template.exchange(base.toString()+"1",HttpMethod.PUT,request, Plan.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Plan new title is "+responseEntity.getBody().getTitle(),plan.getTitle(),responseEntity.getBody().getTitle());


    }

    @Test
    public void deletePlan(){

        int beforeDeleteNumberElements=this.getCurrentNumberOfElements();

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = template.exchange(base.toString()+"6",HttpMethod.DELETE,request, ResponseEntity.class);

        int afterDeleteNumberElements=this.getCurrentNumberOfElements();
        int difference=beforeDeleteNumberElements-afterDeleteNumberElements;
        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Difference is "+difference,1,difference);


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

    }




}


