Feature:  User Student can review a teacher
  Scenario: User Student successfully creates a review of a teacher
    Given Student with a username "jesus.student" is authenticated
    And teacher with username  is "albert.teacher"
    When student fills review form with 5 stars
    And and description "is a really good teacher"
    And click on save review
    Then review response status is 200
    And review is created




