package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.ConsentFormPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Consent Form Management API endpoints (Home Care Portal).
 */
public class HCPConsentFormsStep extends ApiRequestBuilder {

    private static String hcpConsentFormUuid;

    @Given("I set up the HCP request structure to get the consent form list")
    public void setupHCPGetConsentFormList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "created"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(queryParams);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP consent form list is returned successfully with {int} status code")
    public void verifyHCPConsentFormListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            hcpConsentFormUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured HCP Consent Form UUID: " + hcpConsentFormUuid);
        } catch (Exception e) {
            System.out.println("No consent forms found: " + e.getMessage());
        }
    }

    @Given("I set up the HCP request structure to get consent form by ID")
    public void setupHCPGetConsentFormById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Consent Form UUID must be available from list step", hcpConsentFormUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(hcpConsentFormUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP consent form details are returned successfully with {int} status code")
    public void verifyHCPConsentFormDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Consent form data should not be null", response.jsonPath().get("data"));
    }

    @Given("I set up the HCP request structure to create a consent form")
    public void setupHCPCreateConsentForm(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = ConsentFormPayloadGenerator.generateCreateConsentFormPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the HCP consent form is created successfully with {int} status code")
    public void verifyHCPConsentFormCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the HCP request structure to archive the consent form")
    public void setupHCPArchiveConsentForm(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Consent Form UUID must be available from list step", hcpConsentFormUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        String fullPath = endpoint + "/" + hcpConsentFormUuid + "/archive-status/" + status;
        response = request.put(fullPath);
    }

    @Then("I verify that the HCP consent form archive status is updated with {int} status code")
    public void verifyHCPConsentFormArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
