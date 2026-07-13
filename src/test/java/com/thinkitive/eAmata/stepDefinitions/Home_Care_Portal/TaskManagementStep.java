package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.TaskPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Task Management API endpoints (Home Care Portal).
 */
public class TaskManagementStep extends ApiRequestBuilder {

    private static String taskUuid;

    // --- Create Task ---

    @Given("I set up the HCP request structure to create a task")
    public void setupHCPCreateTask(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = TaskPayloadGenerator.generateCreateTaskPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the task is created successfully with {int} status code")
    public void verifyTaskCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the HCP request structure to create a task with invalid data")
    public void setupHCPCreateTaskInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = TaskPayloadGenerator.generateInvalidTaskPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the task creation fails with {int} status code")
    public void verifyTaskCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Task List ---

    @Given("I set up the HCP request structure to get the task list")
    public void setupHCPGetTaskList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "id"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "ASC"));
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(queryParams);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the task list is returned successfully with {int} status code")
    public void verifyTaskListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            taskUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured Task UUID: " + taskUuid);
        } catch (Exception e) {
            System.out.println("No tasks found in list: " + e.getMessage());
        }
    }

    // --- Get Task By ID ---

    @Given("I set up the HCP request structure to get task by ID")
    public void setupHCPGetTaskById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Task UUID must be available from list step", taskUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(taskUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the task details are returned successfully with {int} status code")
    public void verifyTaskDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Task data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Task ---

    @Given("I set up the HCP request structure to update the task")
    public void setupHCPUpdateTask(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Task UUID must be available from list step", taskUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(taskUuid);
        execute(io.restassured.http.Method.GET, endpoint);
        Map<String, Object> existingTask = response.jsonPath().getMap("data");
        Map<String, Object> updatedTask = TaskPayloadGenerator.generateUpdateTaskPayload(existingTask);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(updatedTask);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the task is updated successfully with {int} status code")
    public void verifyTaskUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Update Task Status ---

    @Given("I set up the HCP request structure to update task status")
    public void setupHCPUpdateTaskStatus(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Task UUID must be available from list step", taskUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        String fullPath = endpoint + "/" + taskUuid + "/status/" + status;
        response = request.put(fullPath);
    }

    @Then("I verify that the task status is updated with {int} status code")
    public void verifyTaskStatusUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Archive Task ---

    @Given("I set up the HCP request structure to archive the task")
    public void setupHCPArchiveTask(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Task UUID must be available from list step", taskUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        String fullPath = endpoint + "/" + taskUuid + "/archive-status/" + status;
        response = request.delete(fullPath);
    }

    @Then("I verify that the task archive status is updated with {int} status code")
    public void verifyTaskArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
