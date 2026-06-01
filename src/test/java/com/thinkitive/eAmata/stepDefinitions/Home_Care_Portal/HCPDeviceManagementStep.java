package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Device Management API endpoints (Home Care Portal).
 */
public class HCPDeviceManagementStep extends ApiRequestBuilder {

    private static String hcpDeviceUuid;

    @Given("I set up the HCP request structure to get the device list")
    public void setupHCPGetDeviceList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "created"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("category")) queryParams.put("category", data.get("category"));
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(queryParams);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP device list is returned successfully with {int} status code")
    public void verifyHCPDeviceListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            hcpDeviceUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured HCP Device UUID: " + hcpDeviceUuid);
        } catch (Exception e) {
            System.out.println("No devices found: " + e.getMessage());
        }
    }

    @Given("I set up the HCP request structure to get device by ID")
    public void setupHCPGetDeviceById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Device UUID must be available from list step", hcpDeviceUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(hcpDeviceUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP device details are returned successfully with {int} status code")
    public void verifyHCPDeviceDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Device data should not be null", response.jsonPath().get("data"));
    }
}
