package com.evertix.tutofastbackend.BDDTests.stepdef;

import com.evertix.tutofastbackend.resource.ReviewResource;
import com.evertix.tutofastbackend.resource.ReviewSaveResource;
import com.evertix.tutofastbackend.resource.UserResource;
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
    //private ResponseEntity<MessageResponse> messageResponse;
    private String studentUsername;
    private Long studentId;
    private String teacherUsername;
    private Long teacherId;
    @Before
    public void setUp() throws MalformedURLException {
        this.base=new URL( "http://localhost:" + port + "/api/auth/reviews");
        this.resource= new ReviewSaveResource();

    }


    @Given("Student with a username {string} is authenticated")
    public void studentWithAUsername(String student) {
        this.studentUsername=student;
        this.token=authenticate(studentUsername,"password");
        Assert.assertNotNull("Authentication Failed",token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<UserResource> responseType = new ParameterizedTypeReference<UserResource>() { };
        ResponseEntity<UserResource> responseEntity = template.exchange("http://localhost:" + port + "/api/users/"+studentUsername, HttpMethod.GET,request,responseType);

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());
        this.studentId=responseEntity.getBody().getId();

    }

    @And("teacher with username  is {string}")
    public void teacherWithUsernameIs(String teacher) {
        this.teacherUsername=teacher;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<UserResource> responseType = new ParameterizedTypeReference<UserResource>() { };
        ResponseEntity<UserResource> responseEntity = template.exchange("http://localhost:" + port + "/api/users/"+teacherUsername, HttpMethod.GET,request,responseType);
        this.teacherId=responseEntity.getBody().getId();
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

        Assert.assertEquals(responseEntity.getStatusCodeValue(),200,responseEntity.getStatusCodeValue());

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
}
