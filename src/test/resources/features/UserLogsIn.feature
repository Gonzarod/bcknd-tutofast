Feature:  User Log In in the platform
  Scenario: User admin successfully logs in
    Given User with a username is "jose.admin"
    And password  is "password"
    When he clicks on login button
    Then user is authenticated

  Scenario: User student successfully logs in
    Given User with a username is "jesus.student"
    And password  is "password"
    When he clicks on login button
    Then user is authenticated

  Scenario: User teacher successfully logs in
    Given User with a username is "albert.teacher"
    And password  is "password"
    When he clicks on login button
    Then user is authenticated




