@UsersManagement @HomeCarePortal
Feature: Users Management (Provider/Nurse/Staff) API - Home Care Portal

  @Smoke @Regression
  @HCPCreateProvider
  Scenario: Verify that HCP admin can create a new provider/nurse
    Given I set up the HCP request structure to create a provider
      | endpoint | provider |
    Then I verify that the HCP provider is created successfully with 201 status code

  @Smoke @Regression
  @HCPGetProviderList
  Scenario: Verify that HCP admin can get the provider list
    Given I set up the HCP request structure to get the HCP provider list
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
    Then I verify that the HCP provider list is returned successfully with 200 status code

  @Regression
  @HCPGetProviderById
  Scenario: Verify that HCP admin can get a provider by UUID
    Given I set up the HCP request structure to get the HCP provider list
      | endpoint      | provider |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | id       |
      | sortDirection | desc     |
    Then I verify that the HCP provider list is returned successfully with 200 status code
    Given I set up the HCP request structure to get provider by ID
      | endpoint | provider |
    Then I verify that the HCP provider details are returned successfully with 200 status code

  @Regression
  @HCPCreateUser
  Scenario: Verify that HCP admin can create a staff user
    Given I set up the HCP request structure to create a staff user
      | endpoint | user |
    Then I verify that the HCP user is created successfully with 201 status code

  @Smoke @Regression
  @HCPGetUserList
  Scenario: Verify that HCP admin can get the user list
    Given I set up the HCP request structure to get the user list
      | endpoint      | user |
      | page          | 0    |
      | size          | 10   |
      | sortBy        | id   |
      | sortDirection | desc |
    Then I verify that the HCP user list is returned successfully with 200 status code
