package com.thinkitive.eAmata.stepDefinitions.Admin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.payloads.CarePlanPayload;
import entities.payloads.CarePlanPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Care Plan Management API endpoints (Admin Portal).
 */
public class CarePlanManagementStep extends ApiRequestBuilder {

    private static String carePlanUuid;
    private static String carePlanTitle;

    // --- Create Care Plan ---

    @Given("I set up the request structure to create a care plan")
    public void setupCreateCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        CarePlanPayload payload = CarePlanPayloadGenerator.generateCreateCarePlanPayload();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the care plan is created successfully with {int} status code")
    public void verifyCarePlanCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        String expectedMessage = "Care plan created successfully.";
        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Given("I set up the request structure to create a care plan with invalid data")
    public void setupCreateCarePlanInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        CarePlanPayload payload = CarePlanPayloadGenerator.generateInvalidCarePlanPayload();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the care plan creation fails with {int} status code")
    public void verifyCarePlanCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Care Plan List ---

    @Given("I set up the request structure to get the care plan list")
    public void setupGetCarePlanList(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "modified"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("searchString")) {
            queryParams.put("searchString", data.get("searchString"));
        }

        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    @Then("I verify that the care plan list is displayed with {int} status code")
    public void verifyCarePlanListDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));

        // Capture first care plan UUID for subsequent tests
        try {
            carePlanUuid = response.jsonPath().getString("data.content[0].uuid");
            carePlanTitle = response.jsonPath().getString("data.content[0].title");
            System.out.println("Captured Care Plan UUID: " + carePlanUuid);
            System.out.println("Captured Care Plan Title: " + carePlanTitle);
        } catch (Exception e) {
            System.out.println("No care plans found in list: " + e.getMessage());
        }
    }

    // --- Get Care Plan By ID ---

    @Given("I set up the request structure to get care plan by ID")
    public void setupGetCarePlanById(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("Care Plan UUID must be available from list step", carePlanUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, carePlanUuid, endpoint);
    }

    @Then("I verify that the care plan details are displayed with {int} status code")
    public void verifyCarePlanDetailsDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Care plan data should not be null", response.jsonPath().get("data"));
        Assert.assertNotNull("Care plan UUID should not be null", response.jsonPath().getString("data.uuid"));
    }

    // --- Update Care Plan ---

    @Given("I set up the request structure to update a care plan")
    public void setupUpdateCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("Care Plan UUID must be available from list step", carePlanUuid);

        // Fetch existing care plan details, then modify
        ApiRequestBuilder.GetByIdAPI(superAdminToken, carePlanUuid, endpoint);
        Map<String, Object> existingCarePlan = response.jsonPath().getMap("data");

        // Update only the title
        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        existingCarePlan.put("title", "Updated Care Plan " + faker.medical().diseaseName());

        resetRequest();
        setRequestStructure(superAdminToken);
        setRequestBody(existingCarePlan);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the care plan is updated with {int} status code")
    public void verifyCarePlanUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        String expectedMessage = "Care plan updated successfully.";
        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    // --- Enable/Disable Care Plan ---

    @Given("I set up the request structure to disable the care plan")
    public void setupDisableCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");

        Assert.assertNotNull("Care Plan UUID must be available from list step", carePlanUuid);
        ApiRequestBuilder.PutStatusAPI(superAdminToken, carePlanUuid, status, endpoint);
    }

    @Then("I verify that the care plan status is updated with {int} status code")
    public void verifyCarePlanStatusUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Archive Care Plan ---

    @Given("I set up the request structure to archive the care plan")
    public void setupArchiveCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");

        Assert.assertNotNull("Care Plan UUID must be available from list step", carePlanUuid);
        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, carePlanUuid, "archive-status/" + status, endpoint);
    }

    @Then("I verify that the care plan archive status is updated with {int} status code")
    public void verifyCarePlanArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Protocols ---

    @Given("I set up the request structure to get protocols")
    public void setupGetProtocols(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String protocolType = data.get("protocolType");

        resetRequest();
        setRequestStructure(superAdminToken);
        response = request.get(endpoint + "/" + protocolType);
    }

    @Then("I verify that protocols are displayed with {int} status code")
    public void verifyProtocolsDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Reference Ranges ---

    @Given("I set up the request structure to get reference ranges")
    public void setupGetReferenceRanges(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        resetRequest();
        setRequestStructure(superAdminToken);
        response = request.get(endpoint);
    }

    @Then("I verify that reference ranges are displayed with {int} status code")
    public void verifyReferenceRangesDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
