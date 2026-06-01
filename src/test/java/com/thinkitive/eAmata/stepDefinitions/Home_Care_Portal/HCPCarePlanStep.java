package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.CarePlanPayload;
import entities.payloads.CarePlanPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Care Plan Management API endpoints (Home Care Portal).
 */
public class HCPCarePlanStep extends ApiRequestBuilder {

    private static String hcpCarePlanUuid;

    // --- Get Care Plan List ---

    @Given("I set up the HCP request structure to get the care plan list")
    public void setupHCPGetCarePlanList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "id"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(queryParams);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP care plan list is returned successfully with {int} status code")
    public void verifyHCPCarePlanListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            hcpCarePlanUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured HCP Care Plan UUID: " + hcpCarePlanUuid);
        } catch (Exception e) {
            System.out.println("No care plans found: " + e.getMessage());
        }
    }

    // --- Create Care Plan ---

    @Given("I set up the HCP request structure to create a care plan")
    public void setupHCPCreateCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        CarePlanPayload payload = CarePlanPayloadGenerator.generateCreateCarePlanPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the HCP care plan is created successfully with {int} status code")
    public void verifyHCPCarePlanCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Care Plan By ID ---

    @Given("I set up the HCP request structure to get care plan by ID")
    public void setupHCPGetCarePlanById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Care Plan UUID must be available from list step", hcpCarePlanUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(hcpCarePlanUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP care plan details are returned successfully with {int} status code")
    public void verifyHCPCarePlanDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Care plan data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Care Plan ---

    @Given("I set up the HCP request structure to update the care plan")
    public void setupHCPUpdateCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Care Plan UUID must be available from list step", hcpCarePlanUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(hcpCarePlanUuid);
        execute(io.restassured.http.Method.GET, endpoint);
        Map<String, Object> existing = response.jsonPath().getMap("data");
        existing.put("description", "Updated care plan description " + System.currentTimeMillis());
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(existing);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the HCP care plan is updated successfully with {int} status code")
    public void verifyHCPCarePlanUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Archive Care Plan ---

    @Given("I set up the HCP request structure to archive the care plan")
    public void setupHCPArchiveCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Care Plan UUID must be available from list step", hcpCarePlanUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        String fullPath = endpoint + "/" + hcpCarePlanUuid + "/archive-status/" + status;
        response = request.put(fullPath);
    }

    @Then("I verify that the HCP care plan archive status is updated with {int} status code")
    public void verifyHCPCarePlanArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
