@AuditLogsHCP @HomeCarePortal
Feature: Audit Logs API - Home Care Portal

  @Smoke @Regression
  @HCPGetAuditLogs
  Scenario: Verify that HCP admin can get audit logs
    Given I set up the HCP request structure to get audit logs
      | endpoint | audit-logs |
      | page     | 0                     |
      | size     | 20                    |
    Then I verify that the HCP audit logs are returned successfully with 200 status code
