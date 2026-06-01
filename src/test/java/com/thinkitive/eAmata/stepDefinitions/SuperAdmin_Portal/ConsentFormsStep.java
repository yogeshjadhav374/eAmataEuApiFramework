package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.payloads.ConsentFormPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Consent Form Management API endpoints (Super Admin Portal).
 */
public class ConsentFormsStep extends ApiRequestBuilder {

    private static String consentFormUuid;

    // --- Create Consent Form ---

    @Given("I set up the request structure to create a consent form")
    public void setupCreateConsentForm(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = ConsentFormPayloadGenerator.generateCreateConsentFormPayload();
        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the consent form is created successfully with {int} status code")
    public void verifyConsentFormCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the request structure to create a consent form with invalid data")
    public void setupCreateConsentFormInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = ConsentFormPayloadGenerator.generateInvalidConsentFormPayload();
        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the consent form creation fails with {int} status code")
    public void verifyConsentFormCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Consent Form List ---

    @Given("I set up the request structure to get the consent form list")
    public void setupGetConsentFormList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "created"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("archive")) queryParams.put("archive", data.get("archive"));
        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    @Then("I verify that the consent form list is returned successfully with {int} status code")
    public void verifyConsentFormListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            consentFormUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured Consent Form UUID: " + consentFormUuid);
        } catch (Exception e) {
            System.out.println("No consent forms found: " + e.getMessage());
        }
    }

    // --- Get Consent Form By ID ---

    @Given("I set up the request structure to get consent form by ID")
    public void setupGetConsentFormById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Consent Form UUID must be available from list step", consentFormUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, consentFormUuid, endpoint);
    }

    @Then("I verify that the consent form details are returned successfully with {int} status code")
    public void verifyConsentFormDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Consent form data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Consent Form ---

    @Given("I set up the request structure to update the consent form")
    public void setupUpdateConsentForm(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Consent Form UUID must be available from list step", consentFormUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, consentFormUuid, endpoint);
        Map<String, Object> existing = response.jsonPath().getMap("data");
        existing.put("name", "Updated Consent Form " + System.currentTimeMillis());
        ApiRequestBuilder.PutAPI(superAdminToken, existing, endpoint);
    }

    @Then("I verify that the consent form is updated successfully with {int} status code")
    public void verifyConsentFormUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Deactivate Consent Form (required before archiving) ---

    @Given("I set up the request structure to deactivate the consent form first")
    public void setupDeactivateConsentForm(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Consent Form UUID must be available from list step", consentFormUuid);
        // Fetch existing consent form and set active=false
        ApiRequestBuilder.GetByIdAPI(superAdminToken, consentFormUuid, endpoint);
        Map<String, Object> existing = response.jsonPath().getMap("data");
        existing.put("active", false);
        ApiRequestBuilder.PutAPI(superAdminToken, existing, endpoint);
    }

    @Then("I verify that the consent form deactivation is successful")
    public void verifyConsentFormDeactivated() {
        response.prettyPrint();
        Assert.assertTrue("Consent form deactivation should succeed",
                response.getStatusCode() == 200 || response.getStatusCode() == 201);
    }

    // --- Archive Consent Form ---

    @Given("I set up the request structure to archive the consent form")
    public void setupArchiveConsentForm(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Consent Form UUID must be available from list step", consentFormUuid);
        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, consentFormUuid, "archive-status/" + status, endpoint);
    }

    @Then("I verify that the consent form archive status is updated with {int} status code")
    public void verifyConsentFormArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
