package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.payloads.DevicePayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Device Management API endpoints (Super Admin Portal).
 */
public class DeviceManagementStep extends ApiRequestBuilder {

    private static String deviceUuid;

    // --- Create Device ---

    @Given("I set up the request structure to create a device")
    public void setupCreateDevice(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = DevicePayloadGenerator.generateCreateDevicePayload();
        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the device is created successfully with {int} status code")
    public void verifyDeviceCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the request structure to create a device with invalid data")
    public void setupCreateDeviceInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = DevicePayloadGenerator.generateInvalidDevicePayload();
        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the device creation fails with {int} status code")
    public void verifyDeviceCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Device List ---

    @Given("I set up the request structure to get the device list")
    public void setupGetDeviceList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "created"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("category")) queryParams.put("category", data.get("category"));
        if (data.containsKey("archive")) queryParams.put("archive", data.get("archive"));
        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    @Then("I verify that the device list is returned successfully with {int} status code")
    public void verifyDeviceListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            deviceUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured Device UUID: " + deviceUuid);
        } catch (Exception e) {
            System.out.println("No devices found in list: " + e.getMessage());
        }
    }

    // --- Get Device By ID ---

    @Given("I set up the request structure to get device by ID")
    public void setupGetDeviceById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Device UUID must be available from list step", deviceUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, deviceUuid, endpoint);
    }

    @Then("I verify that the device details are returned successfully with {int} status code")
    public void verifyDeviceDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Device data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Device ---

    @Given("I set up the request structure to update the device")
    public void setupUpdateDevice(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Device UUID must be available from list step", deviceUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, deviceUuid, endpoint);
        Map<String, Object> existingDevice = response.jsonPath().getMap("data");
        existingDevice.put("description", "Updated description " + System.currentTimeMillis());
        ApiRequestBuilder.PutAPI(superAdminToken, existingDevice, endpoint);
    }

    @Then("I verify that the device is updated successfully with {int} status code")
    public void verifyDeviceUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Deactivate Device (required before archiving) ---

    @Given("I set up the request structure to deactivate the device first")
    public void setupDeactivateDevice(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Device UUID must be available from list step", deviceUuid);
        // PUT /device/{uuid}/status/false — deactivate
        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, deviceUuid, "status/false", endpoint);
    }

    @Then("I verify that the device deactivation is successful")
    public void verifyDeviceDeactivated() {
        response.prettyPrint();
        Assert.assertTrue("Device deactivation should succeed",
                response.getStatusCode() == 200 || response.getStatusCode() == 201);
    }

    // --- Archive Device ---

    @Given("I set up the request structure to archive the device")
    public void setupArchiveDevice(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Device UUID must be available from list step", deviceUuid);
        ApiRequestBuilder.PutCustomPathAPI(superAdminToken, deviceUuid, "archive-status/" + status, endpoint);
    }

    @Then("I verify that the device archive status is updated with {int} status code")
    public void verifyDeviceArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
