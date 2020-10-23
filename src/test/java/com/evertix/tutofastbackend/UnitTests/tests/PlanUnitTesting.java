package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.exception.ExceptionResponse;
import com.evertix.tutofastbackend.model.Plan;
import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import com.evertix.tutofastbackend.util.RestPageImpl;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

public class PlanUnitTesting extends UnitTest {

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/plans/");
    }

    @Test
    public void GetAllPlans(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
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
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<PlanResource> responseEntity = template.exchange(base.toString()+"2", HttpMethod.GET,request,PlanResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Plan title is "+responseEntity.getBody().getTitle(),"Basic",responseEntity.getBody().getTitle());

    }

    @Test
    public void GetPlanById_NotFound(){
        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<ExceptionResponse> responseEntity = template.exchange(base.toString()+"50", HttpMethod.GET,request,ExceptionResponse.class);

        Assert.assertEquals("Error Code is: "+responseEntity.getBody().getErrorCode(),"NOT_FOUND",responseEntity.getBody().getErrorCode());
        Assert.assertEquals("Error Message is "+responseEntity.getBody().getErrorMessage(),"Plan with Id: 50 not found",responseEntity.getBody().getErrorMessage());

    }

    @Test
    public void createPlan(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        PlanSaveResource plan = new PlanSaveResource("TestPlan","Test","Test",
                (short) 4, BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP));



        HttpEntity<?> request = new HttpEntity<>(plan, headers);

        ResponseEntity<PlanResource> responseEntity = template.postForEntity(base.toString(),request, PlanResource.class);

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

    @Override
    public int getCurrentNumberOfElements(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<PlanResource>> responseType = new ParameterizedTypeReference<RestPageImpl<PlanResource>>() { };
        ResponseEntity<RestPageImpl<PlanResource>> responseEntity = template.exchange(base.toString(), HttpMethod.GET,request,responseType);

        return (int) responseEntity.getBody().getTotalElements();

    }

}


