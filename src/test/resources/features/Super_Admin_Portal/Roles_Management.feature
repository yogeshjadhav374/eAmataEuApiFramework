@RolesManagement @SuperAdminPortal
Feature: Roles and Privileges Management API

  @Smoke @Regression
  @GetRoles
  Scenario: Verify that user can get all roles
    Given I set up the request structure to get all roles
      | endpoint | role |
    Then I verify that the roles list is returned successfully with 200 status code

  @Regression
  @UpdateRolePrivileges
  Scenario: Verify that user can update role privileges
    Given I set up the request structure to get all roles
      | endpoint | role |
    Then I verify that the roles list is returned successfully with 200 status code
    Given I set up the request structure to update role privileges
      | endpoint | role/privileges |
    Then I verify that the role privileges are updated with 200 status code

  @Regression
  @ResetRolePrivileges
  Scenario: Verify that user can reset role privileges to default
    Given I set up the request structure to reset role privileges
      | endpoint | role/privileges/reset |
    Then I verify that the role privileges are reset with 200 status code
