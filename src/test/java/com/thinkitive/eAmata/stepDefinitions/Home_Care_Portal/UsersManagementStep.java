package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.ProviderPayloadGenerator;
import entities.payloads.StaffPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Users Management (Provider/Nurse/Staff) API endpoints (Home Care Portal).
 */
public class UsersManagementStep extends ApiRequestBuilder {

    private static String hcpProviderUuid;
    private static String hcpUserUuid;

    // --- Create Provider ---

    @Given("I set up the HCP request structure to create a provider")
    public void setupHCPCreateProvider(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = ProviderPayloadGenerator.generateCreateProviderPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the HCP provider is created successfully with {int} status code")
    public void verifyHCPProviderCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get HCP Provider List ---

    @Given("I set up the HCP request structure to get the HCP provider list")
    public void setupHCPGetProviderList(Map<String, String> data) {
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

    @Then("I verify that the HCP provider list is returned successfully with {int} status code")
    public void verifyHCPProviderListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            hcpProviderUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured HCP Provider UUID: " + hcpProviderUuid);
        } catch (Exception e) {
            System.out.println("No providers found: " + e.getMessage());
        }
    }

    // --- Get HCP Provider By ID ---

    @Given("I set up the HCP request structure to get provider by ID")
    public void setupHCPGetProviderById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Provider UUID must be available from list step", hcpProviderUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(hcpProviderUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP provider details are returned successfully with {int} status code")
    public void verifyHCPProviderDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Provider data should not be null", response.jsonPath().get("data"));
    }

    // --- Create Staff User ---

    @Given("I set up the HCP request structure to create a staff user")
    public void setupHCPCreateStaffUser(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = StaffPayloadGenerator.generateCreateStaffPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the HCP user is created successfully with {int} status code")
    public void verifyHCPUserCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get User List ---

    @Given("I set up the HCP request structure to get the user list")
    public void setupHCPGetUserList(Map<String, String> data) {
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

    @Then("I verify that the HCP user list is returned successfully with {int} status code")
    public void verifyHCPUserListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            hcpUserUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured HCP User UUID: " + hcpUserUuid);
        } catch (Exception e) {
            System.out.println("No users found: " + e.getMessage());
        }
    }
}
