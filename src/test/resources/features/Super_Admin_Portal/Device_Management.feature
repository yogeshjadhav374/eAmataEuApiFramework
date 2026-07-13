@DeviceManagement @SuperAdminPortal
Feature: Device Management API

  @Smoke @Regression
  @CreateDevice
  Scenario: Verify that user can create a new device
    Given I set up the request structure to create a device
      | endpoint | device |
    Then I verify that the device is created successfully with 200 status code

  @Regression
  @CreateDeviceInvalidData
  Scenario: Verify that creating a device with invalid data fails
    Given I set up the request structure to create a device with invalid data
      | endpoint | device |
    Then I verify that the device creation fails with 400 status code

  @Smoke @Regression
  @GetDeviceList
  Scenario: Verify that user can get the device list
    Given I set up the request structure to get the device list
      | endpoint      | device |
      | page          | 0      |
      | size          | 10     |
      | sortBy        | created |
      | sortDirection | desc   |
    Then I verify that the device list is returned successfully with 200 status code

  @Regression
  @GetDeviceListByCategory
  Scenario: Verify that user can filter devices by category
    Given I set up the request structure to get the device list
      | endpoint      | device   |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | created  |
      | sortDirection | desc     |
      | category      | DIGITAL  |
    Then I verify that the device list is returned successfully with 200 status code

  @Regression
  @GetDeviceById
  Scenario: Verify that user can get a device by UUID
    Given I set up the request structure to get the device list
      | endpoint      | device |
      | page          | 0      |
      | size          | 10     |
      | sortBy        | created |
      | sortDirection | desc   |
    Then I verify that the device list is returned successfully with 200 status code
    Given I set up the request structure to get device by ID
      | endpoint | device |
    Then I verify that the device details are returned successfully with 200 status code

  @Regression
  @UpdateDevice
  Scenario: Verify that user can update a device
    Given I set up the request structure to get the device list
      | endpoint      | device |
      | page          | 0      |
      | size          | 10     |
      | sortBy        | created |
      | sortDirection | desc   |
    Then I verify that the device list is returned successfully with 200 status code
    Given I set up the request structure to update the device
      | endpoint | device |
    Then I verify that the device is updated successfully with 200 status code

  @Regression
  @ArchiveDevice
  Scenario: Verify that user can archive a device
    Given I set up the request structure to get the device list
      | endpoint      | device |
      | page          | 0      |
      | size          | 10     |
      | sortBy        | created |
      | sortDirection | desc   |
    Then I verify that the device list is returned successfully with 200 status code
    Given I set up the request structure to deactivate the device first
      | endpoint | device |
    Then I verify that the device deactivation is successful
    Given I set up the request structure to archive the device
      | endpoint | device |
      | status   | true   |
    Then I verify that the device archive status is updated with 200 status code
