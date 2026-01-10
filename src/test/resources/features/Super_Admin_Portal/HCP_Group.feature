@VerifyHCPGroup
Feature: Verify HCP Group Feature

  @Regression
  @AddHCPGroupWithValidDetails
  Scenario: Verify that the user is able to Add HCP Group
    Given I set up the request structure to add the HCP Group
      | endpoint | home-care-provider |
    Then I verify that the HCP Group is added successfully with 201 status code

  @Regression
  @getTheHCPGroupList
  Scenario: Verify that the user is able to see the staff list
    Given I set up the request structure to see the HCP Group list
      | endpoint      | home-care-provider |
      | page          | 0              |
      | size          | 100             |
      | sortBy        | created        |
      | sortDirection | desc           |
    Then I verify that the HCP Group list is displayed successfully with 200 status code

  @editHCPGroupWithValidDetails
  Scenario: Verify that the user is able to edit the staff details
    Given I set up the request structure to edit the HCP Group
      | endpoint | home-care-provider |
    Then I verify that the HCP Group is edited successfully with 200 status code


  @getHCPGroupDetailsByValidUUID
  Scenario: Verify that the user is able to view the Provider Group details
    Given I set up the request structure to view the HCP Group details
      | endpoint | home-care-provider                       |
      | uuid     | 90ef8e1f-187d-4746-a450-4d06f8e9b4bf |
    Then I verify that the HCP Group details displayed successfully with 200 status code