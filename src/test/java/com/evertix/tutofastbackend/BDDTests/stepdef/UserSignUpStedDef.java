package com.evertix.tutofastbackend.BDDTests.stepdef;

import com.evertix.tutofastbackend.resource.UserResource;
import com.evertix.tutofastbackend.security.payload.request.SignUpRequest;
import com.evertix.tutofastbackend.security.payload.response.MessageResponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserSignUpStedDef extends TutofastStepDef{

    private ResponseEntity<UserResource> responseEntity;
    private ResponseEntity<MessageResponse> messageResponse;
    private SignUpRequest signUpRequest;

    @Before
    public void setUp() throws MalformedURLException {
        this.base=new URL( "http://localhost:" + port + "/api/auth/signup");
        System.out.println("Base is:"+base.toString()+"-------------------------------------------------->");
    }

    @Given("User with name {string} and dni {string} completes teacher sign up form with username {string} and mail {string}")
    public void userWithNameAndDniCompletesTeacherSignUpFormWithUsernameAndMail(String name, String dni,String username,String mail) {

         this.signUpRequest = this.setUpNewUser(username,"random",mail,name,
                "random",dni,"000000000", LocalDate.now(), "random","teacher");

    }

    @When("When he clicks on sign up button")
    public void whenHeClicksOnSignUpButton() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(signUpRequest, headers);

        this.responseEntity = template.postForEntity(base.toString(),request,UserResource.class);
    }

    @When("When he clicks on sign up buttom")
    public void whenHeClicksOnSignUpButtom() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(signUpRequest, headers);

        this.messageResponse = template.postForEntity(base.toString(),request,MessageResponse.class);
    }

    @Then("sign up response status is {int}")
    public void responseStatusIs(int status) {
        Assert.assertEquals(responseEntity.getStatusCodeValue(),status,responseEntity.getStatusCodeValue());

    }
    @Then("sign up response status will be {int}")
    public void responseStatusWillBe(int status) {
        Assert.assertEquals(messageResponse.getStatusCodeValue(),status,messageResponse.getStatusCodeValue());
    }

    @And("user is successfully registered")
    public void userIsSuccessfullyRegistered() {
        Assert.assertEquals(responseEntity.getBody().getUsername(),signUpRequest.getUsername(),responseEntity.getBody().getUsername());
    }

    @And("sign up message response is {string}")
    public void messageResponseIs(String message) {
        Assert.assertEquals(message,this.messageResponse.getBody().getMessage());
    }



    public SignUpRequest setUpNewUser(String username, String password, String email, String name,
                                             String lastName, String dni, String phone, LocalDate birthday,String address,String role){
        Set<String> roles = new HashSet<>();
        if(role.equals("student")){
            roles.add("ROLE_STUDENT");
        }else if (role.equals("teacher")){
            roles.add("ROLE_TEACHER");
        }

        return new SignUpRequest(username,password,email,roles,name,
                lastName,dni,phone, birthday, address);


    }



}
