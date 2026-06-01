@DeviceManagementHCP @HomeCarePortal
Feature: Device Management API - Home Care Portal

  @Smoke @Regression
  @HCPGetDeviceList
  Scenario: Verify that HCP admin can get the device list
    Given I set up the HCP request structure to get the device list
      | endpoint      | device  |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | created |
      | sortDirection | desc    |
    Then I verify that the HCP device list is returned successfully with 200 status code

  @Regression
  @HCPGetDeviceById
  Scenario: Verify that HCP admin can get a device by UUID
    Given I set up the HCP request structure to get the device list
      | endpoint      | device  |
      | page          | 0       |
      | size          | 10      |
      | sortBy        | created |
      | sortDirection | desc    |
    Then I verify that the HCP device list is returned successfully with 200 status code
    Given I set up the HCP request structure to get device by ID
      | endpoint | device |
    Then I verify that the HCP device details are returned successfully with 200 status code

  @Regression
  @HCPFilterDeviceByCategory
  Scenario: Verify that HCP admin can filter devices by category
    Given I set up the HCP request structure to get the device list
      | endpoint      | device   |
      | page          | 0        |
      | size          | 10       |
      | sortBy        | created  |
      | sortDirection | desc     |
      | category      | DIGITAL  |
    Then I verify that the HCP device list is returned successfully with 200 status code
