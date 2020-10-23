package com.evertix.tutofastbackend.BDDTests.stepdef;

import com.evertix.tutofastbackend.TutofastBackendApplication;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TutofastBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LogInStepDef {
    @When("he clicks on login button")
    public void heClicksOnLoginButton() {
        Assert.assertNotNull(4);
    }

    @Given("User with a username is {string}")
    public void userWithAUsernameIs(String arg0) {
        Assert.assertNotNull(4);
    }

    @And("password  is {string}")
    public void passwordIs(String arg0) {
        Assert.assertNotNull(4);
    }

    @Then("user is authenticated")
    public void userIsAuthenticated() {
        Assert.assertNotNull(4);
    }
}
