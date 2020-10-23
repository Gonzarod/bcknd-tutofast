package com.evertix.tutofastbackend.UnitTests.tests;

import com.evertix.tutofastbackend.TutofastBackendApplication;
import com.evertix.tutofastbackend.security.payload.request.LoginRequest;
import com.evertix.tutofastbackend.security.payload.response.JwtResponse;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TutofastBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class UnitTest {

    protected String token;

    @LocalServerPort
    protected int port;

    protected URL base;

    @Autowired
    protected TestRestTemplate template;

    public String getAuthenticationJWT(String username,String password){

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

    public abstract int getCurrentNumberOfElements();
}
