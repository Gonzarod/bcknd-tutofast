package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.exception.ExceptionResponse;
import com.evertix.tutofastbackend.resource.CourseSaveResource;
import com.evertix.tutofastbackend.util.RestPageImpl;
import com.evertix.tutofastbackend.resource.CourseResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
import java.util.List;

public class CourseUnitTesting extends TutofastUnitTest {

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/courses");
    }

    @Test
    public void GetAllCourses(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<CourseResource>> responseType = new ParameterizedTypeReference<RestPageImpl<CourseResource>>() { };
        ResponseEntity<RestPageImpl<CourseResource>> responseEntity = template.exchange(base.toString()+"/page", HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Size is "+responseEntity.getBody().getTotalElements(),5,responseEntity.getBody().getTotalElements());

    }

    @Test
    public void GetCourseById(){
        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<CourseResource> responseEntity = template.exchange(base.toString()+"/1", HttpMethod.GET,request,CourseResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());

        Assert.assertEquals("Course name is "+responseEntity.getBody().getName(),"Spanish",responseEntity.getBody().getName());
    }

    @Test
    public void GetCourseById_NotFound(){
        this.token=getAuthenticationJWT("jesus.student","password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<ExceptionResponse> responseEntity = template.exchange(base.toString()+"/50", HttpMethod.GET,request,ExceptionResponse.class);

        Assert.assertEquals("Error Code is: "+responseEntity.getBody().getErrorCode(),"NOT_FOUND",responseEntity.getBody().getErrorCode());
        Assert.assertEquals("Error Message is "+responseEntity.getBody().getErrorMessage(),"Course with Id: 50 not found",responseEntity.getBody().getErrorMessage());

    }

    @Test
    public void getAllCourseByName() {

        this.token = getAuthenticationJWT("jose.admin", "password");
        Assert.assertNotNull("Authentication Failed", token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);

        ParameterizedTypeReference<RestPageImpl<CourseResource>> responseType = new ParameterizedTypeReference<RestPageImpl<CourseResource>>() {
        };
        ResponseEntity<RestPageImpl<CourseResource>> responseEntity = template.exchange(base.toString() + "/page?name=geo", HttpMethod.GET, request, responseType);
        List<CourseResource> coursesWithGeo = responseEntity.getBody().getContent();
        Assert.assertEquals(responseEntity.getStatusCodeValue(), 200, responseEntity.getStatusCodeValue());
        //System.out.println(coursesWithGeo.size());
        for (CourseResource course : coursesWithGeo) {
            //System.out.println(coursesWithGeo.getName());
            Assert.assertTrue("Name is: "+course.getName(),course.getName().toLowerCase().contains("geo"));
        }
    }

    @Test
    public void createCourse(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        CourseSaveResource course = new CourseSaveResource("Germany","Germany");

        HttpEntity<?> request = new HttpEntity<>(course, headers);

        ResponseEntity<CourseResource> responseEntity = template.postForEntity(base.toString(),request, CourseResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Course name is "+responseEntity.getBody().getName(),"Germany",responseEntity.getBody().getName());

    }

    @Test
    public void updateCourse(){

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        CourseSaveResource course = new CourseSaveResource("Español","Español");

        HttpEntity<?> request = new HttpEntity<>(course, headers);

        ResponseEntity<CourseResource> responseEntity = template.exchange(base.toString()+"/2",HttpMethod.PUT,request, CourseResource.class);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Course new title is "+responseEntity.getBody().getName(),course.getName(),responseEntity.getBody().getName());

    }

    @Test
    public void deleteCourse(){

        int beforeDeleteNumberElements=this.getCurrentNumberOfElements();

        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<?> responseEntity = template.exchange(base.toString()+"/5",HttpMethod.DELETE,request, ResponseEntity.class);

        int afterDeleteNumberElements=this.getCurrentNumberOfElements();
        int difference=beforeDeleteNumberElements-afterDeleteNumberElements;
        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        Assert.assertEquals("Difference is "+difference,1,difference);

    }

    @Override
    public int getCurrentNumberOfElements() {
        this.token=getAuthenticationJWT("jose.admin","password");
        Assert.assertNotNull("Authentication Failed",token);
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<CourseResource>> responseType = new ParameterizedTypeReference<RestPageImpl<CourseResource>>() { };
        ResponseEntity<RestPageImpl<CourseResource>> responseEntity = template.exchange(base.toString()+"/page", HttpMethod.GET,request,responseType);

        return (int) responseEntity.getBody().getTotalElements();
    }



}
