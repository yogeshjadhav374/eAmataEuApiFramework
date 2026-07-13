@Scheduling @HomeCarePortal
Feature: Scheduling (Appointments) API

  @Smoke @Regression
  @CreateAppointment
  Scenario: Verify that HCP admin can create a new appointment
    Given I set up the HCP request structure to create an appointment
      | endpoint | appointment |
    Then I verify that the appointment is created successfully with 201 status code

  @Smoke @Regression
  @GetAppointmentList
  Scenario: Verify that HCP admin can get the appointment list
    Given I set up the HCP request structure to get the appointment list
      | endpoint      | appointment |
      | page          | 0           |
      | size          | 10          |
      | sortBy        | id          |
      | sortDirection | desc        |
    Then I verify that the appointment list is returned successfully with 200 status code

  @Regression
  @GetAppointmentById
  Scenario: Verify that HCP admin can get an appointment by UUID
    Given I set up the HCP request structure to get the appointment list
      | endpoint      | appointment |
      | page          | 0           |
      | size          | 10          |
      | sortBy        | id          |
      | sortDirection | desc        |
    Then I verify that the appointment list is returned successfully with 200 status code
    Given I set up the HCP request structure to get appointment by ID
      | endpoint | appointment |
    Then I verify that the appointment details are returned successfully with 200 status code

  @Regression
  @UpdateAppointment
  Scenario: Verify that HCP admin can update an appointment
    Given I set up the HCP request structure to get the appointment list
      | endpoint      | appointment |
      | page          | 0           |
      | size          | 10          |
      | sortBy        | id          |
      | sortDirection | desc        |
    Then I verify that the appointment list is returned successfully with 200 status code
    Given I set up the HCP request structure to update the appointment
      | endpoint | appointment |
    Then I verify that the appointment is updated successfully with 200 status code

  @Regression
  @UpdateAppointmentStatus
  Scenario: Verify that HCP admin can update appointment status
    Given I set up the HCP request structure to get the appointment list
      | endpoint      | appointment |
      | page          | 0           |
      | size          | 10          |
      | sortBy        | id          |
      | sortDirection | desc        |
    Then I verify that the appointment list is returned successfully with 200 status code
    Given I set up the HCP request structure to update appointment status
      | endpoint | appointment/update-status |
      | status   | CANCELLED                 |
    Then I verify that the appointment status is updated with 200 status code
