package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.resource.PlanResource;
import com.evertix.tutofastbackend.resource.ReviewResource;
import com.evertix.tutofastbackend.resource.ReviewSaveResource;
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



public class ReviewUnitTesting extends TutofastUnitTest {
    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/reviews/");
    }

    @Test
    public void GetAllReviews(){

        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<ReviewResource>> responseType = new ParameterizedTypeReference<RestPageImpl<ReviewResource>>() { };
        ResponseEntity<RestPageImpl<ReviewResource>> responseEntity = template.exchange(base.toString(), HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Size is "+responseEntity.getBody().getTotalElements(),1,responseEntity.getBody().getTotalElements());

    }

    @Test
    public void GetReviewByTeacherId(){
        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<ReviewResource>> responseType = new ParameterizedTypeReference<RestPageImpl<ReviewResource>>() { };
        ResponseEntity<RestPageImpl<ReviewResource>> responseEntity = template.exchange(base.toString()+"teacher/3", HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        //Assert.assertEquals("Size is "+responseEntity.getBody().getTotalElements(),1,responseEntity.getBody().getTotalElements());

    }

    @Test
    public void makeReview(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        ReviewSaveResource resource = new ReviewSaveResource();
        resource.setDescription("Good technique");
        resource.setStars((short) 4);




        HttpEntity<?> request = new HttpEntity<>(resource, headers);

        ResponseEntity<ReviewResource> responseEntity = template.postForEntity(base.toString()+"student/2/teacher/3",request, ReviewResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Review desc is "+responseEntity.getBody().getDescription(),"Good technique",responseEntity.getBody().getDescription());


    }


    @Test
    public void deleteReview(){

        int beforeDeleteNumberElements=this.getCurrentNumberOfElements();

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = template.exchange(base.toString()+"1",HttpMethod.DELETE,request, ResponseEntity.class);

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
