@CarePlanAdmin @AdminPortal
Feature: Care Plan Management API

  @Smoke @Regression
  @CreateCarePlanAdmin
  Scenario: Verify that user can create a care plan
    Given I set up the request structure to create a care plan
      | endpoint | care-plan |
    Then I verify that the care plan is created successfully with 201 status code

  @Regression
  @CreateCarePlanWithInvalidData
  Scenario: Verify that creating care plan with invalid data fails
    Given I set up the request structure to create a care plan with invalid data
      | endpoint | care-plan |
    Then I verify that the care plan creation fails with 400 status code

  @Smoke @Regression
  @GetCarePlanList
  Scenario: Verify that user can get the care plan list
    Given I set up the request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | modified  |
      | sortDirection | desc      |
    Then I verify that the care plan list is displayed with 200 status code

  @Regression
  @GetCarePlanById
  Scenario: Verify that user can get care plan details by ID
    Given I set up the request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | modified  |
      | sortDirection | desc      |
    Then I verify that the care plan list is displayed with 200 status code
    Given I set up the request structure to get care plan by ID
      | endpoint | care-plan |
    Then I verify that the care plan details are displayed with 200 status code

  @Regression
  @UpdateCarePlanAdmin
  Scenario: Verify that user can update a care plan
    Given I set up the request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | modified  |
      | sortDirection | desc      |
    Then I verify that the care plan list is displayed with 200 status code
    Given I set up the request structure to update a care plan
      | endpoint | care-plan |
    Then I verify that the care plan is updated with 200 status code

  @Regression
  @EnableDisableCarePlan
  Scenario: Verify that user can enable/disable a care plan
    Given I set up the request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | modified  |
      | sortDirection | desc      |
    Then I verify that the care plan list is displayed with 200 status code
    Given I set up the request structure to disable the care plan
      | endpoint | care-plan |
      | status   | false     |
    Then I verify that the care plan status is updated with 200 status code

  @Regression
  @ArchiveCarePlan
  Scenario: Verify that user can archive a care plan
    Given I set up the request structure to get the care plan list
      | endpoint      | care-plan |
      | page          | 0         |
      | size          | 10        |
      | sortBy        | modified  |
      | sortDirection | desc      |
    Then I verify that the care plan list is displayed with 200 status code
    Given I set up the request structure to archive the care plan
      | endpoint | care-plan |
      | status   | true      |
    Then I verify that the care plan archive status is updated with 200 status code

  @Regression
  @GetProtocols
  Scenario: Verify that user can get protocols by type
    Given I set up the request structure to get protocols
      | endpoint     | care-plan/protocols |
      | protocolType | OUT_OF_RANGE_BP     |
    Then I verify that protocols are displayed with 200 status code

  @Regression
  @GetReferenceRanges
  Scenario: Verify that user can get reference ranges
    Given I set up the request structure to get reference ranges
      | endpoint | care-plan/reference-ranges |
    Then I verify that reference ranges are displayed with 200 status code
