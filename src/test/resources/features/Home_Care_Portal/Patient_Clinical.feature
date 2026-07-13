@PatientClinical @HomeCarePortal
Feature: Patient Clinical Sub-Modules API

  # Allergies, Diagnoses, Medications and Vitals for a patient.
  # Every scenario first captures an existing patient to attach the clinical record to.

  # ==================== ALLERGIES ====================

  @Smoke @Regression
  @CreateAllergy @PatientAllergy
  Scenario: Verify that an allergy can be added for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add an allergy for the patient
    Then I verify that the clinical record is created successfully with 201 status code

  @Regression
  @GetAllergyList @PatientAllergy
  Scenario: Verify that the allergy list can be retrieved for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to get the allergy list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code

  @Regression
  @GetAllergyById @PatientAllergy
  Scenario: Verify that an allergy can be retrieved by ID
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add an allergy for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the allergy list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to get the allergy by ID
    Then I verify that the clinical record details are returned with 200 status code

  @Regression
  @UpdateAllergy @PatientAllergy
  Scenario: Verify that an allergy can be updated
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add an allergy for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the allergy list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to update the allergy
    Then I verify that the clinical record is updated successfully with 200 status code

  @Regression
  @ArchiveAllergy @PatientAllergy
  Scenario: Verify that an allergy can be archived
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add an allergy for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the allergy list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to archive the allergy
    Then I verify that the clinical record archive status is updated with 200 status code

  # ==================== DIAGNOSES ====================

  @Smoke @Regression
  @CreateDiagnosis @PatientDiagnosis
  Scenario: Verify that a diagnosis can be added for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a diagnosis for the patient
    Then I verify that the clinical record is created successfully with 201 status code

  @Regression
  @GetDiagnosisList @PatientDiagnosis
  Scenario: Verify that the diagnosis list can be retrieved for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to get the diagnosis list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code

  @Regression
  @UpdateDiagnosis @PatientDiagnosis
  Scenario: Verify that a diagnosis can be updated
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a diagnosis for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the diagnosis list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to update the diagnosis
    Then I verify that the clinical record is updated successfully with 200 status code

  @Regression
  @ArchiveDiagnosis @PatientDiagnosis
  Scenario: Verify that a diagnosis can be archived
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a diagnosis for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the diagnosis list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to archive the diagnosis
    Then I verify that the clinical record archive status is updated with 200 status code

  # ==================== MEDICATIONS ====================

  @Smoke @Regression
  @CreateMedication @PatientMedication
  Scenario: Verify that a medication can be added for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a medication for the patient
    Then I verify that the clinical record is created successfully with 201 status code

  @Regression
  @CreateMedicationInvalid @PatientMedication
  Scenario: Verify that adding a medication with missing required fields fails
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a medication with missing required fields
    Then I verify that the clinical record creation fails with 400 status code

  # Backend returns HTTP 500 on GET /patient-medication for existing patients in QA;
  # tagged @KnownIssue until the server-side defect is fixed.
  @Regression @KnownIssue
  @GetMedicationList @PatientMedication
  Scenario: Verify that the medication list can be retrieved for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to get the medication list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code

  # Depends on the medication list (GET 500 in QA) to capture the record; @KnownIssue.
  @Regression @KnownIssue
  @UpdateMedication @PatientMedication
  Scenario: Verify that a medication can be updated
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a medication for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the medication list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to update the medication
    Then I verify that the clinical record is updated successfully with 200 status code

  # Depends on the medication list (GET 500 in QA) to capture the record; @KnownIssue.
  @Regression @KnownIssue
  @ArchiveMedication @PatientMedication
  Scenario: Verify that a medication can be archived
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add a medication for the patient
    Then I verify that the clinical record is created successfully with 201 status code
    Given I set up the request structure to get the medication list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code
    Given I set up the request structure to archive the medication
    Then I verify that the clinical record archive status is updated with 200 status code

  # ==================== VITALS ====================

  @Smoke @Regression
  @CreateVitals @PatientVitals
  Scenario: Verify that vitals can be recorded for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to add vitals for the patient
    Then I verify that the clinical record is created successfully with 201 status code

  @Regression
  @GetVitalsList @PatientVitals
  Scenario: Verify that the vitals list can be retrieved for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to get the vitals list for the patient
    Then I verify that the clinical list is returned successfully with 200 status code

  @Regression
  @GetLatestVitals @PatientVitals
  Scenario: Verify that the latest vitals can be retrieved for a patient
    Given I capture an existing patient UUID from the patient list
    And I set up the request structure to get the latest vitals for the patient
    Then I verify that the clinical record details are returned with 200 status code
