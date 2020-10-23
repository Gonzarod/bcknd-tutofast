package com.evertix.tutofastbackend.UnitTests.tests;

public class UserUnitTesting extends UnitTest {

    @Override
    public int getCurrentNumberOfElements() {
        return 0;
    }

/*
    @Test
    public void getAllCourseTeacher() {

        this.token = getAuthenticationJWT("jose.admin", "password");
        Assert.assertNotNull("Authentication Failed", token);
        //System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ParameterizedTypeReference<RestPageImpl<CourseResource>> responseType = new ParameterizedTypeReference<RestPageImpl<CourseResource>>() {
        };
        ResponseEntity<RestPageImpl<CourseResource>> responseEntity = template.exchange(base.toString() + "/name/geo", HttpMethod.GET, request, responseType);
        List<CourseResource> coursesWithGeo = responseEntity.getBody().getContent();
        Assert.assertEquals(responseEntity.getStatusCodeValue(), 200, responseEntity.getStatusCodeValue());
        //System.out.println(coursesWithGeo.size());
        for (CourseResource course : coursesWithGeo) {
            //System.out.println(coursesWithGeo.getName());
            Assert.assertTrue("Name is: "+course.getName(),course.getName().toLowerCase().contains("geo"));
        }
    }

    String getAuthenticationJWT(String username,String password){

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

 */


}
