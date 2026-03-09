@Authentication @AdminPortal
Feature: Authentication API

  @Smoke @Regression
  @LoginWithValidCredentials
  Scenario: Verify that user can login with valid credentials
    Given I set up the request structure to login with valid credentials
      | endpoint | login |
    Then I verify that the login is successful with 200 status code

  @Regression
  @LoginWithInvalidCredentials
  Scenario: Verify that login fails with invalid credentials
    Given I set up the request structure to login with invalid credentials
      | endpoint | login |
    Then I verify that the login fails with 400 status code

  @Regression
  @LoginWithBlankUsername
  Scenario: Verify that login fails with blank username
    Given I set up the request structure to login with blank username
      | endpoint | login |
    Then I verify that the login fails with 400 status code

  @Regression
  @LogoutWithValidToken
  Scenario: Verify that user can logout successfully
    Given I set up the request structure to login with valid credentials
      | endpoint | login |
    And I set up the request structure to logout
      | endpoint | logout |
    Then I verify that the logout is successful with 200 status code

  @Regression
  @RefreshToken
  Scenario: Verify that user can refresh access token
    Given I set up the request structure to login with valid credentials
      | endpoint | login |
    And I set up the request structure to refresh the access token
      | endpoint | access-token |
    Then I verify that the token refresh is successful with 200 status code

  @Regression
  @VerifyUserEmail
  Scenario: Verify that existing user email can be verified
    Given I set up the request structure to verify user email
      | endpoint | verify-user |
    Then I verify that the email verification response is 200 status code

  @Regression
  @ChangePasswordWithInvalidOldPassword
  Scenario: Verify that change password fails with wrong old password
    Given I set up the request structure to change password with invalid old password
      | endpoint | change-password |
    Then I verify that change password fails with 400 status code
