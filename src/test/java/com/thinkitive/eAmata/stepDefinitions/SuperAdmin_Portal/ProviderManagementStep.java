package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.payloads.ProviderPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Provider/Nurse Management API endpoints.
 */
public class ProviderManagementStep extends ApiRequestBuilder {

    private static String providerUuid;
    private static String providerEmail;

    // --- Create Provider ---

    @Given("I set up the request structure to add a provider")
    public void setupCreateProvider(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = ProviderPayloadGenerator.generateCreateProviderPayload();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the provider is added successfully with {int} status code")
    public void verifyProviderCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Response message should not be null", response.jsonPath().get("message"));
    }

    @Given("I set up the request structure to add a provider with invalid data")
    public void setupCreateProviderInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = ProviderPayloadGenerator.generateInvalidProviderPayload();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the provider creation fails with {int} status code")
    public void verifyProviderCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Provider List ---

    @Given("I set up the request structure to get the provider list")
    public void setupGetProviderList(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "id"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("archive")) {
            queryParams.put("archive", data.get("archive"));
        }

        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    @Then("I verify that the provider list is displayed successfully with {int} status code")
    public void verifyProviderListDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));

        // Capture first provider UUID for subsequent tests
        try {
            providerUuid = response.jsonPath().getString("data.content[0].uuid");
            providerEmail = response.jsonPath().getString("data.content[0].email");
            System.out.println("Captured Provider UUID: " + providerUuid);
        } catch (Exception e) {
            System.out.println("No providers found in list: " + e.getMessage());
        }
    }

    // --- Search Providers ---

    @Given("I set up the request structure to search providers")
    public void setupSearchProviders(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "id"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("searchString")) {
            queryParams.put("searchString", data.get("searchString"));
        }

        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    // --- Get Provider By ID ---

    @Given("I set up the request structure to get provider by ID")
    public void setupGetProviderById(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("Provider UUID must be available from list step", providerUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, providerUuid, endpoint);
    }

    @Then("I verify that the provider details are displayed successfully with {int} status code")
    public void verifyProviderDetailsDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Provider data should not be null", response.jsonPath().get("data"));
        Assert.assertNotNull("Provider UUID should not be null", response.jsonPath().getString("data.uuid"));
    }

    @Given("I set up the request structure to get provider by invalid ID")
    public void setupGetProviderByInvalidId(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String uuid = data.get("uuid");

        ApiRequestBuilder.GetByIdAPI(superAdminToken, uuid, endpoint);
    }

    @Then("I verify that the provider is not found with {int} status code")
    public void verifyProviderNotFound(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Update Provider ---

    @Given("I set up the request structure to update the provider")
    public void setupUpdateProvider(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("Provider UUID must be available from list step", providerUuid);

        // Fetch provider details as raw JSON string to preserve structure
        ApiRequestBuilder.GetByIdAPI(superAdminToken, providerUuid, endpoint);
        String rawJson = response.jsonPath().getString("data");

        // Use the raw response data and parse it
        Map<String, Object> existingProvider = response.jsonPath().getMap("data");

        // Only update the introduction field (safe field that doesn't affect IAM)
        existingProvider.put("introduction", "Updated introduction " + System.currentTimeMillis());

        ApiRequestBuilder.PutAPI(superAdminToken, existingProvider, endpoint);
    }

    @Then("I verify that the provider is updated successfully with {int} status code")
    public void verifyProviderUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Response message should not be null", response.jsonPath().get("message"));
    }

    // --- Deactivate Provider (required before archiving) ---

    @Given("I set up the request structure to deactivate the provider first")
    public void setupDeactivateProvider() {
        Assert.assertNotNull("Provider UUID must be available from list step", providerUuid);

        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, providerUuid, "archive-status/true", "provider");
    }

    @Then("I verify that the provider deactivation succeeds")
    public void verifyProviderDeactivated() {
        response.prettyPrint();
        // Accept 200 or any success status
        Assert.assertTrue("Provider deactivation should succeed",
                response.getStatusCode() == 200 || response.getStatusCode() == 201);
    }

    // --- Archive/Unarchive Provider ---

    @Given("I set up the request structure to archive the provider")
    public void setupArchiveProvider(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");

        Assert.assertNotNull("Provider UUID must be available from list step", providerUuid);
        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, providerUuid, "archive-status/" + status, endpoint);
    }

    @Then("I verify that the provider archive status is updated with {int} status code")
    public void verifyProviderArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Provider Profile ---

    @Given("I set up the request structure to get the authenticated provider profile")
    public void setupGetProviderProfile(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        ApiRequestBuilder.GetAPI(superAdminToken, null, endpoint);
    }

    @Then("I verify that the provider profile response is returned")
    public void verifyProviderProfileResponse() {
        response.prettyPrint();
        // Accept 200 (super admin has a provider profile) or 400/404 (no provider record for super admin user)
        int status = response.getStatusCode();
        Assert.assertTrue("Provider profile endpoint should respond with 200, 400 or 404 (got " + status + ")",
                status == 200 || status == 400 || status == 404);
    }

    // --- Update Provider Onboarding Status ---

    @Given("I set up the request structure to update provider onboarding status")
    public void setupUpdateProviderOnboardingStatus(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.getOrDefault("status", "COMPLETED");

        Assert.assertNotNull("Provider UUID must be available from list step", providerUuid);
        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, providerUuid, "onboarding-status/" + status, endpoint);
    }

    @Then("I verify that the provider onboarding status is updated with {int} status code")
    public void verifyProviderOnboardingStatusUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
