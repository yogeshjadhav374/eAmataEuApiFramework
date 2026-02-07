@SuperAdminStaff
Feature: Verify Super Admin Staff

  @Regression
  @AddStaffWithValidDetails
  Scenario: Verify that the user is able to Add staff
    Given I set up the request structure to add the Admin staff
      | endpoint | user |
    Then I verify that the Admin staff is added successfully with 201 status code

  @Regression
  @getTheUsersList
  Scenario: Verify that the user is able to see the staff list
    Given I set up the request structure to see the staff list
      | endpoint      | user        |
      | page          | 0           |
      | size          | 10          |
      | sortBy        | created     |
      | sortDirection | desc        |
      | role          | SUPER_ADMIN |
      | roleType      | STAFF       |
    Then I verify that the Admin staff is see the list of staff successfully with 200 status code

  @editStaffWithValidDetails
  Scenario: Verify that the user is able to edit the staff details
    Given I set up the request structure to edit the staff details
      | endpoint | user |
    Then I verify that the user can edit the staff details successfully with 200 status code


  @getStaffDetailsByValidUUID
  Scenario: Verify that the user is able to view the staff details
    Given I set up the request structure to view the staff details
      | endpoint | user                                 |
      | uuid     | 00739c9f-f703-47f2-9774-a17368fdc628 |
    Then I verify that the user can view the staff details successfully with 200 status code