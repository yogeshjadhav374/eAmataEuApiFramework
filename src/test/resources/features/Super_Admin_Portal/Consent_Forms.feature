@ConsentForms @SuperAdminPortal
Feature: Consent Forms Management API

  @Smoke @Regression
  @CreateConsentForm
  Scenario: Verify that user can create a new consent form
    Given I set up the request structure to create a consent form
      | endpoint | consent-form |
    Then I verify that the consent form is created successfully with 200 status code

  @Regression
  @CreateConsentFormInvalidData
  Scenario: Verify that creating consent form with invalid data fails
    Given I set up the request structure to create a consent form with invalid data
      | endpoint | consent-form |
    Then I verify that the consent form creation fails with 400 status code

  @Smoke @Regression
  @GetConsentFormList
  Scenario: Verify that user can get the consent form list
    Given I set up the request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the consent form list is returned successfully with 200 status code

  @Regression
  @GetConsentFormById
  Scenario: Verify that user can get a consent form by UUID
    Given I set up the request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the consent form list is returned successfully with 200 status code
    Given I set up the request structure to get consent form by ID
      | endpoint | consent-form |
    Then I verify that the consent form details are returned successfully with 200 status code

  @Regression
  @UpdateConsentForm
  Scenario: Verify that user can update a consent form
    Given I set up the request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the consent form list is returned successfully with 200 status code
    Given I set up the request structure to update the consent form
      | endpoint | consent-form |
    Then I verify that the consent form is updated successfully with 200 status code

  @Regression
  @ArchiveConsentForm
  Scenario: Verify that user can archive a consent form
    Given I set up the request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the consent form list is returned successfully with 200 status code
    Given I set up the request structure to deactivate the consent form first
      | endpoint | consent-form |
    Then I verify that the consent form deactivation is successful
    Given I set up the request structure to archive the consent form
      | endpoint | consent-form |
      | status   | true         |
    Then I verify that the consent form archive status is updated with 200 status code
