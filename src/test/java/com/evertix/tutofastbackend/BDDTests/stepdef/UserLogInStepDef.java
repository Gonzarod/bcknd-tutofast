package com.evertix.tutofastbackend.BDDTests.stepdef;

import com.evertix.tutofastbackend.security.payload.request.LoginRequest;
import com.evertix.tutofastbackend.security.payload.response.JwtResponse;
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


public class UserLogInStepDef extends TutofastStepDef {


    private String username;
    private String password;

    @Before
    public void setUp() throws MalformedURLException {
        this.base=new URL( "http://localhost:" + port + "/api/auth/signin");
    }

    @Given("User with a username is {string}")
    public void userWithAUsernameIs(String username) {
        this.username=username;
    }

    @And("password  is {string}")
    public void passwordIs(String password) {
        this.password=password;
    }

    @When("he clicks on login button")
    public void heClicksOnLoginButton() {
        token=this.getAuthenticationJWT(username,password);
    }

    @Then("user is authenticated")
    public void userIsAuthenticated() {
        Assert.assertNotNull("TOKEN IS:" +token,token);
    }

    @Then("user is authentication failed")
    public void userIsAuthenticationFailed() {
        Assert.assertNull(token);
    }


    public String getAuthenticationJWT(String username,String password){

        try{
            HttpHeaders headers = new HttpHeaders();
            LoginRequest loginRequest = new LoginRequest(username, password);
            HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);
            ResponseEntity<JwtResponse> responseEntity = template.postForEntity(this.base.toString(),request, JwtResponse.class);
            return responseEntity.getBody().getToken();
        } catch (Exception e) {
            return null;
        }

    }
}
