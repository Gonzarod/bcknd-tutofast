Feature:  User Student can review a teacher
  Scenario: User Student successfully creates a review of a teacher
    Given Student with a username "jesus.student" is authenticated
    And teacher with username  is "albert.teacher"
    When student fills review form with 5 stars
    And and description "is a really good teacher"
    And click on save review
    Then review response status is 200
    And review is created

  Scenario: User Student successfully creates a review of a teacher
    Given Student with a username "jesus.student" is authenticated
    And teacher with username  is "albert.teacher"
    When student fills review form with 4 stars
    And and description "nice teacher"
    And click on save review
    Then review response status is 200
    And review is created

  Scenario: User Teacher successfully list his reviews
    Given Teacher with a username "albert.teacher" is authenticated
    When clicks on his reviews
    Then review list response status is 200
    And all his reviews are listed




