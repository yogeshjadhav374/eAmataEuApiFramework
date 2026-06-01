@ConsentFormsHCP @HomeCarePortal
Feature: Consent Forms Management API - Home Care Portal

  @Smoke @Regression
  @HCPGetConsentFormList
  Scenario: Verify that HCP admin can get the consent form list
    Given I set up the HCP request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the HCP consent form list is returned successfully with 200 status code

  @Regression
  @HCPGetConsentFormById
  Scenario: Verify that HCP admin can get a consent form by UUID
    Given I set up the HCP request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the HCP consent form list is returned successfully with 200 status code
    Given I set up the HCP request structure to get consent form by ID
      | endpoint | consent-form |
    Then I verify that the HCP consent form details are returned successfully with 200 status code

  @Regression
  @HCPCreateConsentForm
  Scenario: Verify that HCP admin can create a new consent form
    Given I set up the HCP request structure to create a consent form
      | endpoint | consent-form |
    Then I verify that the HCP consent form is created successfully with 200 status code

  @Regression
  @HCPArchiveConsentForm
  Scenario: Verify that HCP admin can archive a consent form
    Given I set up the HCP request structure to get the consent form list
      | endpoint      | consent-form |
      | page          | 0            |
      | size          | 10           |
      | sortBy        | created      |
      | sortDirection | desc         |
    Then I verify that the HCP consent form list is returned successfully with 200 status code
    Given I set up the HCP request structure to archive the consent form
      | endpoint | consent-form |
      | status   | true         |
    Then I verify that the HCP consent form archive status is updated with 200 status code
