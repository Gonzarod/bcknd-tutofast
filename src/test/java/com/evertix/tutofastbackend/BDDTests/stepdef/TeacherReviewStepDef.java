package com.evertix.tutofastbackend.BDDTests.stepdef;

import com.evertix.tutofastbackend.resource.ReviewResource;
import com.evertix.tutofastbackend.resource.ReviewSaveResource;
import com.evertix.tutofastbackend.resource.UserResource;
import com.evertix.tutofastbackend.util.RestPageImpl;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;


public class TeacherReviewStepDef extends TutofastStepDef {

    ReviewSaveResource resource;
    private ResponseEntity<ReviewResource> responseEntity;
    private ResponseEntity<RestPageImpl<ReviewResource>> listresponseEntity;
    //private ResponseEntity<MessageResponse> messageResponse;
    private String studentUsername;
    private Long studentId;
    private String teacherUsername;
    private Long teacherId;
    @Before
    public void setUp() throws MalformedURLException {
        this.base=new URL( "http://localhost:" + port + "/api/reviews");
        this.resource= new ReviewSaveResource();

    }


    @Given("Student with a username {string} is authenticated")
    public void studentWithAUsername(String studentUsername) {
        this.studentUsername=studentUsername;
        this.token=authenticate(studentUsername,"password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<UserResource> studentResponseEntity = template.exchange("http://localhost:" + port + "/api/users/username/"+studentUsername, HttpMethod.GET,request,UserResource.class);
        this.studentId=studentResponseEntity.getBody().getId();
        System.out.println(studentResponseEntity.getBody().getId()+"<<<<<<<<<<<<</*****************************************");
        System.out.println(studentResponseEntity.getBody().getEmail()+"<<<<<<<<<<<<</*****************************************");
        Assert.assertEquals(studentResponseEntity.getStatusCodeValue(),200,studentResponseEntity.getStatusCodeValue());

    }

    @And("teacher with username  is {string}")
    public void teacherWithUsernameIs(String teacher) {
        this.teacherUsername=teacher;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<UserResource> teacherResponseEntity = template.exchange("http://localhost:" + port + "/api/users/username/"+teacherUsername, HttpMethod.GET,request,UserResource.class);
        this.teacherId=teacherResponseEntity.getBody().getId();
        System.out.println("Teacher ID is "+teacherId);
        Assert.assertEquals(teacherResponseEntity.getStatusCodeValue(),200,teacherResponseEntity.getStatusCodeValue());
    }

    @When("student fills review form with {int} stars")
    public void studentFillsReviewFormWithStars(int stars) {
        resource.setStars((short) stars);
    }

    @And("and description {string}")
    public void andDescription(String description) {
        resource.setDescription(description);
    }

    @And("click on save review")
    public void clickOnSaveReview() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(resource, headers);

        this.responseEntity = template.postForEntity(base.toString()+"/student/"+studentId+"/teacher/"+teacherId,request, ReviewResource.class);
        System.out.println(base.toString()+"/student/"+studentId+"/teacher/"+teacherId);
    }

    @And("review is created")
    public void reviewIsCreated() {
        Assert.assertEquals("Review desc is "+responseEntity.getBody().getDescription(),resource.getDescription(),responseEntity.getBody().getDescription());
        Assert.assertEquals("Review stars is "+responseEntity.getBody().getStars(),resource.getStars(),responseEntity.getBody().getStars());
    }


    @Then("review response status is {int}")
    public void reviewResponseStatusIs(int status) {
        Assert.assertEquals(this.responseEntity.getStatusCodeValue(),status,responseEntity.getStatusCodeValue());
    }

    @Given("Teacher with a username {string} is authenticated")
    public void teacherWithAUsernameIsAuthenticated(String teacherUsername) {
        this.teacherUsername=teacherUsername;
        this.token=authenticate(teacherUsername,"password");

        Assert.assertNotNull("Authentication Failed",token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<UserResource> teacherResponseEntity = template.exchange("http://localhost:" + port + "/api/users/username/"+teacherUsername, HttpMethod.GET,request,UserResource.class);
        this.teacherId=teacherResponseEntity.getBody().getId();


    }

    @When("clicks on his reviews")
    public void clicksOnHisReviews() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<ReviewResource>> responseType = new ParameterizedTypeReference<RestPageImpl<ReviewResource>>() { };
        this.listresponseEntity = template.exchange(base.toString()+"/teacher/"+teacherId, HttpMethod.GET,request,responseType);




    }

    @And("all his reviews are listed")
    public void allHisReviewsAreListed() {
        Assert.assertEquals("*************Size is "+listresponseEntity.getBody().getTotalElements(),3,listresponseEntity.getBody().getTotalElements());
    }

    @Then("review list response status is {int}")
    public void reviewListResponseStatusIs(int arg0) {
        Assert.assertEquals(listresponseEntity.getStatusCodeValue(),200,listresponseEntity.getStatusCodeValue());
    }
}
