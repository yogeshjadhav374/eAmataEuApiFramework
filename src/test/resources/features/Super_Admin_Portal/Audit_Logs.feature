@AuditLogs @SuperAdminPortal
Feature: Audit Logs API

  @Smoke @Regression @KnownIssue
  @GetAuditLogs
  Scenario: Verify that user can get audit logs
    Given I set up the request structure to get audit logs
      | endpoint      | activity |
    Then I verify that the audit logs are returned successfully with 200 status code
