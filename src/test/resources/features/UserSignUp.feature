Feature:  User Sign Up in the platform
  Scenario: User teacher successfully signs ups
    Given User with name "Julio Bazan" and dni "66998855" completes teacher sign up form with username "julio.profe" and mail "julio@gmail.com"
    When When he clicks on sign up button
    Then sign up response status is 200
    And user is successfully registered

  Scenario: User teacher signs ups failed
    Given User with name "Julio Bascuñan" and dni "88774455" completes teacher sign up form with username "julio.profe" and mail "juliobas@gmail.com"
    When When he clicks on sign up buttom
    Then sign up response status will be 400
    And sign up message response is "Username is already taken!"

  Scenario: User student successfully signs ups
    Given User with name "Pepe Luna" and dni "22154755" completes teacher sign up form with username "pepe.student" and mail "pepito@gmail.com"
    When When he clicks on sign up button
    Then sign up response status is 200
    And user is successfully registered

  Scenario: User student signs ups failed
    Given User with name "El Pepe" and dni "88884444" completes teacher sign up form with username "pepe.student" and mail "elpepe@gmail.com"
    When When he clicks on sign up buttom
    Then sign up response status will be 400
    And sign up message response is "Username is already taken!"

