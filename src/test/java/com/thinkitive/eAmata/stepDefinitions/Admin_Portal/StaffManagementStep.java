package com.thinkitive.eAmata.stepDefinitions.Admin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.payloads.StaffPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Staff Management API endpoints.
 */
public class StaffManagementStep extends ApiRequestBuilder {

    private static String staffUuid;
    private static String staffEmail;

    // --- Create Staff ---

    @Given("I set up the request structure to create staff")
    public void setupCreateStaff(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = StaffPayloadGenerator.generateCreateStaffPayload();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the staff is created successfully with {int} status code")
    public void verifyStaffCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Response message should not be null", response.jsonPath().get("message"));
    }

    @Given("I set up the request structure to create staff with invalid data")
    public void setupCreateStaffInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = StaffPayloadGenerator.generateInvalidStaffPayload();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the staff creation fails with {int} status code")
    public void verifyStaffCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Staff List ---

    @Given("I set up the request structure to get the staff list")
    public void setupGetStaffList(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "created"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("roleType")) {
            queryParams.put("roleType", data.get("roleType"));
        }

        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    @Then("I verify that the staff list is displayed successfully with {int} status code")
    public void verifyStaffListDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));

        // Capture first staff UUID for subsequent tests
        try {
            staffUuid = response.jsonPath().getString("data.content[0].uuid");
            staffEmail = response.jsonPath().getString("data.content[0].email");
            System.out.println("Captured Staff UUID: " + staffUuid);
        } catch (Exception e) {
            System.out.println("No staff found in list: " + e.getMessage());
        }
    }

    // --- Get Staff List with Role Filter ---

    @Given("I set up the request structure to get the staff list with role filter")
    public void setupGetStaffListWithRoleFilter(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "created"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        if (data.containsKey("roleType")) {
            queryParams.put("roleType", data.get("roleType"));
        }
        if (data.containsKey("role")) {
            queryParams.put("role", data.get("role"));
        }

        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    // --- Get Staff By ID ---

    @Given("I set up the request structure to get staff by ID")
    public void setupGetStaffById(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("Staff UUID must be available from list step", staffUuid);
        ApiRequestBuilder.GetByIdAPI(superAdminToken, staffUuid, endpoint);
    }

    @Then("I verify that the staff details are displayed successfully with {int} status code")
    public void verifyStaffDetailsDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Staff data should not be null", response.jsonPath().get("data"));
        Assert.assertNotNull("Staff UUID should not be null", response.jsonPath().getString("data.uuid"));
    }

    @Given("I set up the request structure to get staff by invalid ID")
    public void setupGetStaffByInvalidId(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String uuid = data.get("uuid");

        ApiRequestBuilder.GetByIdAPI(superAdminToken, uuid, endpoint);
    }

    @Then("I verify that the staff is not found with {int} status code")
    public void verifyStaffNotFound(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Update Staff ---

    @Given("I set up the request structure to update the staff")
    public void setupUpdateStaff(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("Staff UUID must be available from list step", staffUuid);
        Map<String, Object> payload = StaffPayloadGenerator.generateUpdateStaffPayload(staffUuid);
        // Preserve original email to avoid duplicate errors
        if (staffEmail != null) {
            payload.put("email", staffEmail);
        }

        ApiRequestBuilder.PutAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the staff is updated successfully with {int} status code")
    public void verifyStaffUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Response message should not be null", response.jsonPath().get("message"));
    }

    // --- Deactivate Staff (required before archiving) ---

    @Given("I set up the request structure to deactivate the staff first")
    public void setupDeactivateStaff() {
        Assert.assertNotNull("Staff UUID must be available from list step", staffUuid);

        // Fetch the staff, set active=false, then update
        ApiRequestBuilder.GetByIdAPI(superAdminToken, staffUuid, "user");
        java.util.Map<String, Object> staffData = response.jsonPath().getMap("data");
        staffData.put("active", false);

        ApiRequestBuilder.PutAPI(superAdminToken, staffData, "user");
    }

    @Then("I verify that the staff deactivation succeeds")
    public void verifyStaffDeactivated() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());
    }

    // --- Archive Staff ---

    @Given("I set up the request structure to archive the staff")
    public void setupArchiveStaff(Map<String, String> data) {
        String status = data.get("status");

        Assert.assertNotNull("Staff UUID must be available from list step", staffUuid);

        // Backend endpoint: PUT /api/master/{userId}/archive-status/{status}
        String fullPath = staffUuid + "/archive-status/" + status;

        resetRequest();
        setRequestStructure(superAdminToken);
        response = request.put(fullPath);
    }

    @Then("I verify that the staff archive status is updated with {int} status code")
    public void verifyStaffArchiveStatus(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
