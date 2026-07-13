@Authentication @SuperAdminPortal
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

  @Regression
  @LoginWithBlankPassword
  Scenario: Verify that login fails with blank password
    Given I set up the request structure to login with blank password
      | endpoint | login |
    Then I verify that the login fails with 400 status code

  @Regression
  @LogoutWithInvalidRefreshToken
  Scenario: Verify that logout fails with an invalid refresh token
    Given I set up the request structure to login with valid credentials
      | endpoint | login |
    And I set up the request structure to logout with an invalid refresh token
      | endpoint | logout |
    Then I verify that logout fails with 400 status code

  @Regression
  @RefreshTokenWithInvalidToken
  Scenario: Verify that token refresh fails with an invalid refresh token
    Given I set up the request structure to refresh with an invalid refresh token
      | endpoint | refresh-token |
    Then I verify that token refresh fails with 400 status code

  @Regression
  @ResendOtpValidEmail
  Scenario: Verify that OTP can be resent for an existing user email
    Given I set up the request structure to resend OTP for the configured email
      | endpoint | resend-otp |
      | linkType | RESET_PASSWORD |
    Then I verify that the resend OTP response is 200 status code

  @Smoke @Regression
  @GetUserProfile
  Scenario: Verify that an authenticated user can fetch their profile
    Given I set up the request structure to fetch the authenticated user profile
      | endpoint | profile |
    Then I verify that the user profile is returned with 200 status code
