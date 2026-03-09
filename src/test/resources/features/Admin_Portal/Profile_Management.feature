@ProfileManagement @AdminPortal
Feature: Profile Management API

  @Smoke @Regression
  @GetUserProfile
  Scenario: Verify that user can get their own profile
    Given I set up the request structure to get user profile
      | endpoint | profile |
    Then I verify that the profile is displayed successfully with 200 status code

  @Regression
  @GetProfileWithoutAuth
  Scenario: Verify that getting profile without authentication fails
    Given I set up the request structure to get profile without authentication
      | endpoint | profile |
    Then I verify that the request fails with 401 status code

  @Regression
  @ChangeUserAvatar
  Scenario: Verify that user can change their avatar
    Given I set up the request structure to get user profile
      | endpoint | profile |
    Then I verify that the profile is displayed successfully with 200 status code
    Given I set up the request structure to change avatar
      | endpoint | change-avatar |
    Then I verify that the avatar is changed successfully with 200 status code
