@ProviderManagement @AdminPortal
Feature: Provider/Nurse Management API

  @Smoke @Regression
  @CreateProvider
  Scenario: Verify that user can create a new provider/nurse
    Given I set up the request structure to add a provider
      | endpoint | provider |
    Then I verify that the provider is added successfully with 201 status code

  @Regression
  @CreateProviderWithInvalidData
  Scenario: Verify that creating provider with invalid data fails
    Given I set up the request structure to add a provider with invalid data
      | endpoint | provider |
    Then I verify that the provider creation fails with 400 status code

  @Smoke @Regression
  @GetProviderList
  Scenario: Verify that user can get the provider list
    Given I set up the request structure to get the provider list
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
    Then I verify that the provider list is displayed successfully with 200 status code

  @Regression
  @GetProviderListWithSearch
  Scenario: Verify that user can search providers
    Given I set up the request structure to search providers
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
      | searchString  | nurse    |
    Then I verify that the provider list is displayed successfully with 200 status code

  @Regression
  @GetProviderById
  Scenario: Verify that user can get provider details by UUID
    Given I set up the request structure to get the provider list
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
    Then I verify that the provider list is displayed successfully with 200 status code
    Given I set up the request structure to get provider by ID
      | endpoint | provider |
    Then I verify that the provider details are displayed successfully with 200 status code

  @Regression @KnownIssue
  @UpdateProvider
  Scenario: Verify that user can update provider details
    Given I set up the request structure to get the provider list
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
    Then I verify that the provider list is displayed successfully with 200 status code
    Given I set up the request structure to update the provider
      | endpoint | provider |
    Then I verify that the provider is updated successfully with 200 status code

  @Regression @KnownIssue
  @ArchiveProvider
  Scenario: Verify that user can archive a provider
    Given I set up the request structure to get the provider list
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
    Then I verify that the provider list is displayed successfully with 200 status code
    Given I set up the request structure to archive the provider
      | endpoint | provider |
      | status   | true     |
    Then I verify that the provider archive status is updated with 200 status code

  @Regression
  @GetProviderByInvalidId
  Scenario: Verify that getting provider with invalid UUID returns error
    Given I set up the request structure to get provider by invalid ID
      | endpoint | provider                             |
      | uuid     | 00000000-0000-0000-0000-000000000000 |
    Then I verify that the provider is not found with 400 status code
