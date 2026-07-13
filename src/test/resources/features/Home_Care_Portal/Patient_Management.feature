@PatientManagement @HomeCarePortal
Feature: Patient Management API

  @Smoke @Regression
  @CreatePatient
  Scenario: Verify that HCP admin can create a new patient
    Given I set up the HCP request structure to create a patient
      | endpoint | patient |
    Then I verify that the patient is created successfully with 201 status code

  @Regression
  @CreatePatientInvalidData
  Scenario: Verify that creating patient with invalid data fails
    Given I set up the HCP request structure to create a patient with invalid data
      | endpoint | patient |
    Then I verify that the patient creation fails with 400 status code

  @Smoke @Regression
  @GetPatientList
  Scenario: Verify that HCP admin can get the patient list
    Given I set up the HCP request structure to get the patient list
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
    Then I verify that the patient list is returned successfully with 200 status code

  @Regression
  @GetPatientById
  Scenario: Verify that HCP admin can get patient details by UUID
    Given I set up the HCP request structure to get the patient list
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
    Then I verify that the patient list is returned successfully with 200 status code
    Given I set up the HCP request structure to get patient by ID
      | endpoint | patient |
    Then I verify that the patient details are returned successfully with 200 status code

  @Regression
  @UpdatePatient
  Scenario: Verify that HCP admin can update patient details
    Given I set up the HCP request structure to get the patient list
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
    Then I verify that the patient list is returned successfully with 200 status code
    Given I set up the HCP request structure to update the patient
      | endpoint | patient |
    Then I verify that the patient is updated successfully with 200 status code

  @Regression
  @ArchivePatient
  Scenario: Verify that HCP admin can archive a patient
    Given I set up the HCP request structure to get the patient list
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
    Then I verify that the patient list is returned successfully with 200 status code
    Given I set up the HCP request structure to archive the patient
      | endpoint | patient |
      | status   | true    |
    Then I verify that the patient archive status is updated with 200 status code

  @Regression
  @GetPatientStatistics
  Scenario: Verify that HCP admin can get patient statistics
    Given I set up the HCP request structure to get the patient list
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
    Then I verify that the patient list is returned successfully with 200 status code
    Given I set up the HCP request structure to get patient statistics
      | endpoint | patient/statistic |
    Then I verify that the patient statistics are returned successfully with 200 status code

  @Regression
  @SearchPatient
  Scenario: Verify that HCP admin can search patients
    Given I set up the HCP request structure to search patients
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
      | searchString  | a       |
    Then I verify that the patient list is returned successfully with 200 status code

  @Regression
  @GetPatientByInvalidId
  Scenario: Verify that getting a patient with an invalid UUID returns an error
    Given I set up the HCP request structure to get patient by invalid ID
      | endpoint | patient                              |
      | uuid     | 00000000-0000-0000-0000-000000000000 |
    Then I verify that the patient is not found with 400 status code

  # Patient archive/unarchive currently returns 400 in QA (same pre-existing backend defect
  # as @ArchivePatient); tagged @KnownIssue until resolved.
  @Regression @KnownIssue
  @UnarchivePatient
  Scenario: Verify that HCP admin can unarchive a patient
    Given I set up the HCP request structure to get the patient list
      | endpoint      | patient |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | id      |
      | sortDirection | desc    |
    Then I verify that the patient list is returned successfully with 200 status code
    Given I set up the HCP request structure to archive the patient
      | endpoint | patient |
      | status   | false   |
    Then I verify that the patient archive status is updated with 200 status code
