Feature: Verify login Functionality


  @ValidUsernameAndPasswordForSuperAdmin
  Scenario: Verify user is able to login on the super admin Portal
    Given I set up the structure to login User
      | endpoint | login   |
    Then I verify that the user is able to login on the super admin portal successfully
