@MedicalCodes @HomeCarePortal
Feature: Medical Codes Management API

  @Smoke @Regression
  @CreateMedicalCode
  Scenario: Verify that HCP admin can create a medical code
    Given I set up the HCP request structure to create a medical code
      | endpoint | medical-codes |
    Then I verify that the medical code is created successfully with 201 status code

  @Smoke @Regression
  @GetMedicalCodeList
  Scenario: Verify that HCP admin can get the medical code list
    Given I set up the HCP request structure to get the medical code list
      | endpoint      | medical-codes |
      | page          | 0             |
      | size          | 10            |
      | sortBy        | id            |
      | sortDirection | desc          |
    Then I verify that the medical code list is returned successfully with 200 status code

  @Regression
  @GetMedicalCodeById
  Scenario: Verify that HCP admin can get a medical code by UUID
    Given I set up the HCP request structure to get the medical code list
      | endpoint      | medical-codes |
      | page          | 0             |
      | size          | 10            |
      | sortBy        | id            |
      | sortDirection | desc          |
    Then I verify that the medical code list is returned successfully with 200 status code
    Given I set up the HCP request structure to get medical code by ID
      | endpoint | medical-codes |
    Then I verify that the medical code details are returned successfully with 200 status code

  @Regression
  @UpdateMedicalCode
  Scenario: Verify that HCP admin can update a medical code
    Given I set up the HCP request structure to get the medical code list
      | endpoint      | medical-codes |
      | page          | 0             |
      | size          | 10            |
      | sortBy        | id            |
      | sortDirection | desc          |
    Then I verify that the medical code list is returned successfully with 200 status code
    Given I set up the HCP request structure to update the medical code
      | endpoint | medical-codes |
    Then I verify that the medical code is updated successfully with 200 status code

  @Regression
  @ArchiveMedicalCode
  Scenario: Verify that HCP admin can archive a medical code
    Given I set up the HCP request structure to get the medical code list
      | endpoint      | medical-codes |
      | page          | 0             |
      | size          | 10            |
      | sortBy        | id            |
      | sortDirection | desc          |
    Then I verify that the medical code list is returned successfully with 200 status code
    Given I set up the HCP request structure to archive the medical code
      | endpoint | medical-codes |
      | status   | true          |
    Then I verify that the medical code archive status is updated with 200 status code
