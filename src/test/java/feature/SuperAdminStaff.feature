Feature: Verify Super Admin Staff

  @AddStaffWithValidDetails
  Scenario: Verify that the user is able to Add staff
    Given I set up the structure to login User
      | endpoint | login   |
    Given I set up the request structure to add the Admin staff
    |endpoint| user |
    Then I verify that the Admin staff is added successfully with 201 status code

    @getTheUsersList
  Scenario: Verify that the user is able to see the staff list
    Given I set up the structure to login User
      | endpoint | login   |
    Given I set up the request structure to see the staff list
      |endpoint| user |
      |page| 0 |
      |size| 100 |
      |role|SUPER_ADMIN|
      |roleType| STAFF |
    Then I verify that the Admin staff is see the list of staff successfully with 200 status code
