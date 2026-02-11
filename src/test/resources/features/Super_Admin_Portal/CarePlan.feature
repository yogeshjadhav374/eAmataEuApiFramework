@CarePlan
Feature: Verify Care Plan API

  @Regression
  @CreateCarePlan
  Scenario: Verify that the user is able to add care plan
    Given I set up the request structure to add the care plan
      | endpoint      | care-plan |
    Then I verify that the care plan is added successfully with 201 status code

  @GetCarePlan
  Scenario: Verify that the user is able to get care plan list
    Given I set up the request structure to get the care plan
      | endpoint      | care-plan             |
      | page          | 0                     |
      | size          | 10                    |
      | sortBy        | modified              |
      | sortDirection | desc                  |
      | searchString  |                       |
    Then I verify that the status code is 200

  @UpdateCarePlan
  Scenario: Verify that the user is able to update care plan
    Given I set up the request structure to update the care plan
      | endpoint | care-plan |
    Then I verify that the care plan is updated successfully with 200 status code
