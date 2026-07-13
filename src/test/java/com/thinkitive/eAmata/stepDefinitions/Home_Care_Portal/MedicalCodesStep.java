package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.MedicalCodePayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Medical Codes Management API endpoints (Home Care Portal).
 */
public class MedicalCodesStep extends ApiRequestBuilder {

    private static String medicalCodeUuid;

    // --- Create Medical Code ---

    @Given("I set up the HCP request structure to create a medical code")
    public void setupHCPCreateMedicalCode(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = MedicalCodePayloadGenerator.generateCreateMedicalCodePayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the medical code is created successfully with {int} status code")
    public void verifyMedicalCodeCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Medical Code List ---

    @Given("I set up the HCP request structure to get the medical code list")
    public void setupHCPGetMedicalCodeList(Map<String, String> data) {
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

    @Then("I verify that the medical code list is returned successfully with {int} status code")
    public void verifyMedicalCodeListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            medicalCodeUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured Medical Code UUID: " + medicalCodeUuid);
        } catch (Exception e) {
            System.out.println("No medical codes found: " + e.getMessage());
        }
    }

    // --- Get Medical Code By ID ---

    @Given("I set up the HCP request structure to get medical code by ID")
    public void setupHCPGetMedicalCodeById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Medical Code UUID must be available from list step", medicalCodeUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(medicalCodeUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the medical code details are returned successfully with {int} status code")
    public void verifyMedicalCodeDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Medical code data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Medical Code ---

    @Given("I set up the HCP request structure to update the medical code")
    public void setupHCPUpdateMedicalCode(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Medical Code UUID must be available from list step", medicalCodeUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(medicalCodeUuid);
        execute(io.restassured.http.Method.GET, endpoint);
        Map<String, Object> existing = response.jsonPath().getMap("data");
        Map<String, Object> updated = MedicalCodePayloadGenerator.generateUpdateMedicalCodePayload(existing);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(updated);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the medical code is updated successfully with {int} status code")
    public void verifyMedicalCodeUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Archive Medical Code ---

    @Given("I set up the HCP request structure to archive the medical code")
    public void setupHCPArchiveMedicalCode(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Medical Code UUID must be available from list step", medicalCodeUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        String fullPath = endpoint + "/" + medicalCodeUuid + "/archive-status/" + status;
        response = request.put(fullPath);
    }

    @Then("I verify that the medical code archive status is updated with {int} status code")
    public void verifyMedicalCodeArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
