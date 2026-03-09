@StaffManagement @AdminPortal
Feature: Staff Management API

  @Smoke @Regression
  @AddStaff
  Scenario: Verify that user can add a new staff member
    Given I set up the request structure to create staff
      | endpoint | user |
    Then I verify that the staff is created successfully with 201 status code

  @Regression
  @AddStaffWithInvalidData
  Scenario: Verify that creating staff with invalid data fails
    Given I set up the request structure to create staff with invalid data
      | endpoint | user |
    Then I verify that the staff creation fails with 400 status code

  @Smoke @Regression
  @GetStaffList
  Scenario: Verify that user can get the staff list
    Given I set up the request structure to get the staff list
      | endpoint      | user    |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | created |
      | sortDirection | desc    |
      | roleType      | STAFF   |
    Then I verify that the staff list is displayed successfully with 200 status code

  @Regression
  @GetStaffListWithRoleFilter
  Scenario: Verify that user can filter staff by role
    Given I set up the request structure to get the staff list with role filter
      | endpoint      | user        |
      | page          | 0           |
      | size          | 10          |
      | sortBy        | created     |
      | sortDirection | desc        |
      | roleType      | STAFF       |
      | role          | SUPER_ADMIN |
    Then I verify that the staff list is displayed successfully with 200 status code

  @Regression
  @GetStaffById
  Scenario: Verify that user can get staff details by UUID
    Given I set up the request structure to get the staff list
      | endpoint      | user    |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | created |
      | sortDirection | desc    |
      | roleType      | STAFF   |
    Then I verify that the staff list is displayed successfully with 200 status code
    Given I set up the request structure to get staff by ID
      | endpoint | user |
    Then I verify that the staff details are displayed successfully with 200 status code

  @Regression
  @UpdateStaff
  Scenario: Verify that user can update staff details
    Given I set up the request structure to get the staff list
      | endpoint      | user    |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | created |
      | sortDirection | desc    |
      | roleType      | STAFF   |
    Then I verify that the staff list is displayed successfully with 200 status code
    Given I set up the request structure to update the staff
      | endpoint | user |
    Then I verify that the staff is updated successfully with 200 status code

  @Regression
  @ArchiveStaff
  Scenario: Verify that user can archive a staff member (must deactivate first)
    Given I set up the request structure to get the staff list
      | endpoint      | user    |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | created |
      | sortDirection | desc    |
      | roleType      | STAFF   |
    Then I verify that the staff list is displayed successfully with 200 status code
    Given I set up the request structure to deactivate the staff first
    Then I verify that the staff deactivation succeeds
    Given I set up the request structure to archive the staff
      | status | true |
    Then I verify that the staff archive status is updated with 200 status code

  @Regression
  @GetStaffByInvalidId
  Scenario: Verify that getting staff with invalid UUID returns error
    Given I set up the request structure to get staff by invalid ID
      | endpoint | user                                 |
      | uuid     | 00000000-0000-0000-0000-000000000000 |
    Then I verify that the staff is not found with 400 status code
