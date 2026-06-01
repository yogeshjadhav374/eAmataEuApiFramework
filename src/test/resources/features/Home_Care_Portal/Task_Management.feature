@TaskManagement @HomeCarePortal
Feature: Task Management API

  @Smoke @Regression
  @CreateTask
  Scenario: Verify that HCP admin can create a new task
    Given I set up the HCP request structure to create a task
      | endpoint | task |
    Then I verify that the task is created successfully with 201 status code

  @Regression
  @CreateTaskInvalidData
  Scenario: Verify that creating task with invalid data fails
    Given I set up the HCP request structure to create a task with invalid data
      | endpoint | task |
    Then I verify that the task creation fails with 400 status code

  @Smoke @Regression
  @GetTaskList
  Scenario: Verify that HCP admin can get the task list
    Given I set up the HCP request structure to get the task list
      | endpoint      | task |
      | page          | 0    |
      | size          | 10   |
      | sortBy        | id   |
      | sortDirection | ASC  |
    Then I verify that the task list is returned successfully with 200 status code

  @Regression
  @GetTaskById
  Scenario: Verify that HCP admin can get a task by UUID
    Given I set up the HCP request structure to get the task list
      | endpoint      | task |
      | page          | 0    |
      | size          | 10   |
      | sortBy        | id   |
      | sortDirection | ASC  |
    Then I verify that the task list is returned successfully with 200 status code
    Given I set up the HCP request structure to get task by ID
      | endpoint | task |
    Then I verify that the task details are returned successfully with 200 status code

  @Regression
  @UpdateTask
  Scenario: Verify that HCP admin can update a task
    Given I set up the HCP request structure to get the task list
      | endpoint      | task |
      | page          | 0    |
      | size          | 10   |
      | sortBy        | id   |
      | sortDirection | ASC  |
    Then I verify that the task list is returned successfully with 200 status code
    Given I set up the HCP request structure to update the task
      | endpoint | task |
    Then I verify that the task is updated successfully with 200 status code

  @Regression
  @UpdateTaskStatus
  Scenario: Verify that HCP admin can update task status
    Given I set up the HCP request structure to get the task list
      | endpoint      | task |
      | page          | 0    |
      | size          | 10   |
      | sortBy        | id   |
      | sortDirection | ASC  |
    Then I verify that the task list is returned successfully with 200 status code
    Given I set up the HCP request structure to update task status
      | endpoint | task   |
      | status   | CLOSED |
    Then I verify that the task status is updated with 200 status code

  @Regression
  @ArchiveTask
  Scenario: Verify that HCP admin can archive a task
    Given I set up the HCP request structure to get the task list
      | endpoint      | task |
      | page          | 0    |
      | size          | 10   |
      | sortBy        | id   |
      | sortDirection | ASC  |
    Then I verify that the task list is returned successfully with 200 status code
    Given I set up the HCP request structure to archive the task
      | endpoint | task |
      | status   | true |
    Then I verify that the task archive status is updated with 200 status code
