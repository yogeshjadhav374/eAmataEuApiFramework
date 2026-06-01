@RolesManagementHCP @HomeCarePortal
Feature: Roles and Privileges Management API - Home Care Portal

  @Smoke @Regression
  @HCPGetRoles
  Scenario: Verify that HCP admin can get all roles
    Given I set up the HCP request structure to get all roles
      | endpoint | role |
    Then I verify that the HCP roles list is returned successfully with 200 status code

  @Regression
  @HCPUpdateRolePrivileges
  Scenario: Verify that HCP admin can update role privileges
    Given I set up the HCP request structure to get all roles
      | endpoint | role |
    Then I verify that the HCP roles list is returned successfully with 200 status code
    Given I set up the HCP request structure to update role privileges
      | endpoint | role/privileges |
    Then I verify that the HCP role privileges are updated with 200 status code

  @Regression
  @HCPResetRolePrivileges
  Scenario: Verify that HCP admin can reset role privileges to default
    Given I set up the HCP request structure to reset role privileges
      | endpoint | role/privileges/reset |
    Then I verify that the HCP role privileges are reset with 200 status code
