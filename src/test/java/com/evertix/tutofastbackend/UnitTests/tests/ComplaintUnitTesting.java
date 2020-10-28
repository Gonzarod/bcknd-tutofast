package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.exception.ExceptionResponse;
import com.evertix.tutofastbackend.resource.ComplaintResource;
import com.evertix.tutofastbackend.resource.ComplaintSaveResource;
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

public class ComplaintUnitTesting extends TutofastUnitTest {

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/complaints/");
    }

    @Test
    public void GetAllComplaint(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<ComplaintResource>> responseType = new ParameterizedTypeReference<RestPageImpl<ComplaintResource>>() { };
        ResponseEntity<RestPageImpl<ComplaintResource>> responseEntity = template.exchange(base.toString()+"page", HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Size is "+responseEntity.getBody().getTotalElements(),1,responseEntity.getBody().getTotalElements());

    }

    @Test
    public void GetComplaintById(){
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<ComplaintResource> responseEntity = template.exchange(base.toString()+"1", HttpMethod.GET,request, ComplaintResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Complaint reason is "+responseEntity.getBody().getReason(),"Teacher didnt attend to class",responseEntity.getBody().getReason());


    }

    @Test
    public void GetPlanById_NotFound(){
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<ExceptionResponse> responseEntity = template.exchange(base.toString()+"50", HttpMethod.GET,request,ExceptionResponse.class);

        Assert.assertEquals("Error Code is: "+responseEntity.getBody().getErrorCode(),"NOT_FOUND",responseEntity.getBody().getErrorCode());
        Assert.assertEquals("Error Message is "+responseEntity.getBody().getErrorMessage(),"Complaint with id: 50 not found",responseEntity.getBody().getErrorMessage());

    }

    @Test
    public void makeComplaint(){

        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        ComplaintSaveResource resource = new ComplaintSaveResource("Teacher had bad connection","Teacher Albert had bad connection");


        HttpEntity<?> request = new HttpEntity<>(resource, headers);

        ResponseEntity<ComplaintResource> responseEntity = template.postForEntity(base.toString()+"madeBy/2/reported/3",request, ComplaintResource.class);
        //System.out.println(responseEntity.getBody().getMadeBy().getLastName());
        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        //Assert.assertEquals("Complaint reason is "+responseEntity.getBody().getReason(),"Teacher had bad connection",responseEntity.getBody().getReason());


    }


    /*


        @Test
        public void createPlan(){

            this.token=getAuthenticationJWT("jose.admin","password");
            Assert.assertNotNull("Authentication Failed",token);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            PlanSaveResource plan = new PlanSaveResource("TestPlan","Test","Test",
                    (short) 4, BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),false);



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
                    (short) 4, BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),false);

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


     */
    @Override
    public int getCurrentNumberOfElements() {
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<ComplaintResource>> responseType = new ParameterizedTypeReference<RestPageImpl<ComplaintResource>>() { };
        ResponseEntity<RestPageImpl<ComplaintResource>> responseEntity = template.exchange(base.toString(), HttpMethod.GET,request,responseType);

        return (int) responseEntity.getBody().getTotalElements();
    }
}
