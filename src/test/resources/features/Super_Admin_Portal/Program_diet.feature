@ProgramDiet
Feature: Verify Program Diet API

  @Regression
  @CreateProgramDiet
  Scenario: Verify that the user is able to add program diet
    Given I set up the request structure to add the program diet
      | endpoint      | program-diet |
    Then I verify that the program diet is added successfully with 201 status code

  @Regression
  @GetProgramDietList
  Scenario: Verify that the user is able to see the program diet list
    Given I set up the request structure to see the program diet list
      | endpoint      | program-diet |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | modified     |
      | sortDirection | desc         |
    Then I verify that the program diet list is displayed successfully with 200 status code

  @UpdateProgramDiet
  Scenario: Verify that the user is able to update program diet
    Given I set up the request structure to update the program diet
      | endpoint      | program-diet |
    Then I verify that the program diet is updated successfully with 200 status code

  @InactiveProgramDiet
  Scenario: Verify that the user is able to inactive the program diet
    Given I set up the request structure to inactive the program diet
      | endpoint      | program-diet |
    Then I verify that the program diet status is updated successfully with 200 status code

  @DeleteProgramDiet
  Scenario: Verify that the user is able to delete the program diet
    Given I set up the request structure to delete the program diet
      | endpoint      | program-diet |
    Then I verify that the program diet is deleted successfully with 200 status code




