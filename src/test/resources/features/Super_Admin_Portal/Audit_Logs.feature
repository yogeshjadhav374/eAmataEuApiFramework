@AuditLogs @SuperAdminPortal
Feature: Audit Logs API

  # Targets the real Audit Log controller at /api/admin/audit-logs
  # (list + filters, action-types, users, save, CSV export).

  @Smoke @Regression
  @GetAuditLogs
  Scenario: Verify that user can retrieve audit logs
    Given I set up the request structure to retrieve audit logs
      | page | 0  |
      | size | 20 |
    Then I verify that the audit logs are returned successfully with 200 status code

  @Regression
  @GetAuditLogsWithPagination
  Scenario: Verify that audit logs support pagination
    Given I set up the request structure to retrieve audit logs
      | page | 1 |
      | size | 5 |
    Then I verify that the audit logs are returned successfully with 200 status code

  @Regression
  @GetAuditLogsFilterByActionType
  Scenario: Verify that audit logs can be filtered by action type
    Given I set up the request structure to retrieve audit logs
      | page       | 0     |
      | size       | 20    |
      | actionType | LOGIN |
    Then I verify that the audit logs are returned successfully with 200 status code

  @Regression
  @GetAuditLogsFilterByModule
  Scenario: Verify that audit logs can be filtered by module
    Given I set up the request structure to retrieve audit logs
      | page   | 0              |
      | size   | 20             |
      | module | AUTHENTICATION |
    Then I verify that the audit logs are returned successfully with 200 status code

  @Regression
  @GetAuditLogsSearch
  Scenario: Verify that audit logs can be searched by description
    Given I set up the request structure to retrieve audit logs
      | page         | 0     |
      | size         | 20    |
      | searchString | login |
    Then I verify that the audit logs are returned successfully with 200 status code

  @Regression
  @GetAuditLogsDateRange
  Scenario: Verify that audit logs can be filtered by a date range
    Given I set up the request structure to retrieve audit logs for the current month
      | page | 0  |
      | size | 20 |
    Then I verify that the audit logs are returned successfully with 200 status code

  @Smoke @Regression
  @GetAuditActionTypes
  Scenario: Verify that user can retrieve audit action types
    Given I set up the request structure to retrieve audit action types
      | page | 0  |
      | size | 20 |
    Then I verify that the audit action types are returned successfully with 200 status code

  @Regression
  @GetAuditActionTypesSearch
  Scenario: Verify that audit action types can be searched
    Given I set up the request structure to retrieve audit action types
      | page             | 0     |
      | size             | 20    |
      | searchActionType | LOGIN |
    Then I verify that the audit action types are returned successfully with 200 status code

  @Regression
  @GetAuditLogUsers
  Scenario: Verify that user can retrieve audit log users
    Given I set up the request structure to retrieve audit log users
      | page | 0  |
      | size | 20 |
    Then I verify that the audit log users are returned successfully with 200 status code

  @Regression
  @GetAuditLogUsersWithRole
  Scenario: Verify that audit log users can be filtered by role and name
    Given I set up the request structure to retrieve audit log users
      | page   | 0           |
      | size   | 20          |
      | role   | SUPER_ADMIN |
      | search | a           |
    Then I verify that the audit log users are returned successfully with 200 status code

  @Smoke @Regression
  @SaveAuditLog
  Scenario: Verify that a UI-initiated audit log can be saved
    Given I set up the request structure to save a UI-initiated audit log
    Then I verify that the audit log is saved successfully with 201 status code

  @Regression
  @SaveAuditLogMissingFields
  Scenario: Verify that saving an audit log fails with missing required fields
    Given I set up the request structure to save an audit log with missing required fields
    Then I verify that saving the audit log fails with 400 status code

  @Regression
  @DownloadAuditLogsCsv
  Scenario: Verify that audit logs can be downloaded as CSV
    Given I set up the request structure to download audit logs as CSV
      | timezone | UTC |
    Then I verify that the audit logs CSV is downloaded with 200 status code

  @Regression
  @GetAuditLogsUnauthorized
  Scenario: Verify that audit logs request is rejected without a valid token
    Given I set up the request structure to retrieve audit logs without a valid token
    Then I verify that the audit logs request is rejected with 401 status code
