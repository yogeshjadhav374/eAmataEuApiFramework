@Authentication @HomeCarePortal
Feature: Home Care Portal Authentication API

  @Smoke @Regression
  @HCPLogin
  Scenario: Verify that HCP admin can login successfully
    Given I set up the HCP login request with valid credentials
      | endpoint | login |
    Then I verify that the HCP login is successful with 200 status code

  @Regression
  @HCPLoginInvalidCredentials
  Scenario: Verify that login with invalid credentials fails
    Given I set up the HCP login request with invalid credentials
      | endpoint | login |
    Then I verify that the HCP login fails with 401 status code

  @Smoke @Regression
  @HCPRefreshToken
  Scenario: Verify that user can refresh the access token
    Given I set up the HCP login request with valid credentials
      | endpoint | login |
    Then I verify that the HCP login is successful with 200 status code
    Given I set up the request to refresh the HCP access token
      | endpoint | access-token |
    Then I verify that the HCP token refresh is successful with 200 status code

  @Regression
  @HCPLogout
  Scenario: Verify that HCP user can logout successfully
    Given I set up the HCP login request with valid credentials
      | endpoint | login |
    Then I verify that the HCP login is successful with 200 status code
    Given I set up the HCP logout request
      | endpoint | logout |
    Then I verify that the HCP logout is successful with 200 status code
