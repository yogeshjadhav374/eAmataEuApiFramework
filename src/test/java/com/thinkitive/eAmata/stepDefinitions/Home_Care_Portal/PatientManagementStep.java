package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.PatientPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Patient Management API endpoints (Home Care Portal).
 */
public class PatientManagementStep extends ApiRequestBuilder {

    private static String patientUuid;

    // --- Create Patient ---

    @Given("I set up the HCP request structure to create a patient")
    public void setupHCPCreatePatient(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = PatientPayloadGenerator.generateCreatePatientPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the patient is created successfully with {int} status code")
    public void verifyPatientCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the HCP request structure to create a patient with invalid data")
    public void setupHCPCreatePatientInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = PatientPayloadGenerator.generateInvalidPatientPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the patient creation fails with {int} status code")
    public void verifyPatientCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Patient List ---

    @Given("I set up the HCP request structure to get the patient list")
    public void setupHCPGetPatientList(Map<String, String> data) {
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

    @Then("I verify that the patient list is returned successfully with {int} status code")
    public void verifyPatientListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            patientUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured Patient UUID: " + patientUuid);
        } catch (Exception e) {
            System.out.println("No patients found in list: " + e.getMessage());
        }
    }

    // --- Search Patients ---

    @Given("I set up the HCP request structure to search patients")
    public void setupHCPSearchPatients(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "id"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("searchString")) {
            queryParams.put("searchString", data.get("searchString"));
        }
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(queryParams);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    // --- Get Patient By Invalid ID ---

    @Given("I set up the HCP request structure to get patient by invalid ID")
    public void setupHCPGetPatientByInvalidId(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String uuid = data.get("uuid");
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(uuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the patient is not found with {int} status code")
    public void verifyPatientNotFound(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Patient By ID ---

    @Given("I set up the HCP request structure to get patient by ID")
    public void setupHCPGetPatientById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Patient UUID must be available from list step", patientUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(patientUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the patient details are returned successfully with {int} status code")
    public void verifyPatientDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Patient data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Patient ---

    @Given("I set up the HCP request structure to update the patient")
    public void setupHCPUpdatePatient(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Patient UUID must be available from list step", patientUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(patientUuid);
        execute(io.restassured.http.Method.GET, endpoint);
        Map<String, Object> existingPatient = response.jsonPath().getMap("data");
        existingPatient.put("middleName", "Updated " + System.currentTimeMillis());
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(existingPatient);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the patient is updated successfully with {int} status code")
    public void verifyPatientUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Archive Patient ---

    @Given("I set up the HCP request structure to archive the patient")
    public void setupHCPArchivePatient(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Patient UUID must be available from list step", patientUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        String fullPath = endpoint + "/" + patientUuid + "/archive-status/" + status;
        response = request.put(fullPath);
    }

    @Then("I verify that the patient archive status is updated with {int} status code")
    public void verifyPatientArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Patient Statistics ---

    @Given("I set up the HCP request structure to get patient statistics")
    public void setupHCPGetPatientStatistics(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Patient UUID must be available from list step", patientUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(patientUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the patient statistics are returned successfully with {int} status code")
    public void verifyPatientStatisticsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
