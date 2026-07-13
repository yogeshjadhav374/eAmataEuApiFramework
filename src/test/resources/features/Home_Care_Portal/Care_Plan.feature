@CarePlanHCP @HomeCarePortal
Feature: Care Plan Management API - Home Care Portal

  @Smoke @Regression
  @HCPGetCarePlanList
  Scenario: Verify that HCP admin can get the care plan list
    Given I set up the HCP request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | id        |
      | sortDirection | desc      |
    Then I verify that the HCP care plan list is returned successfully with 200 status code

  @Smoke @Regression
  @HCPCreateCarePlan
  Scenario: Verify that HCP admin can create a care plan
    Given I set up the HCP request structure to create a care plan
      | endpoint | care-plan |
    Then I verify that the HCP care plan is created successfully with 201 status code

  @Regression
  @HCPGetCarePlanById
  Scenario: Verify that HCP admin can get a care plan by UUID
    Given I set up the HCP request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | id        |
      | sortDirection | desc      |
    Then I verify that the HCP care plan list is returned successfully with 200 status code
    Given I set up the HCP request structure to get care plan by ID
      | endpoint | care-plan |
    Then I verify that the HCP care plan details are returned successfully with 200 status code

  @Regression
  @HCPUpdateCarePlan
  Scenario: Verify that HCP admin can update a care plan
    Given I set up the HCP request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | id        |
      | sortDirection | desc      |
    Then I verify that the HCP care plan list is returned successfully with 200 status code
    Given I set up the HCP request structure to update the care plan
      | endpoint | care-plan |
    Then I verify that the HCP care plan is updated successfully with 200 status code

  @Regression
  @HCPArchiveCarePlan
  Scenario: Verify that HCP admin can archive a care plan
    Given I set up the HCP request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | id        |
      | sortDirection | desc      |
    Then I verify that the HCP care plan list is returned successfully with 200 status code
    Given I set up the HCP request structure to archive the care plan
      | endpoint | care-plan |
      | status   | true      |
    Then I verify that the HCP care plan archive status is updated with 200 status code
