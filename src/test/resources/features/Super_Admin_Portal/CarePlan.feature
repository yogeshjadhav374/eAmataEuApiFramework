@CarePlan
Feature: Verify Care Plan API

  @Regression
  @CreateCarePlan
  Scenario: Verify that the user is able to add care plan
    Given I set up the request structure to add the care plan
      | endpoint      | care-plan |
    Then I verify that the care plan is added successfully with 201 status code
