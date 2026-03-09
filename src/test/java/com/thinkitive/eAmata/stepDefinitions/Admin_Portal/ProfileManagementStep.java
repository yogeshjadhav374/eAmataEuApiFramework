package com.thinkitive.eAmata.stepDefinitions.Admin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.ProfilePayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.Map;

/**
 * Step definitions for Profile Management API endpoints.
 */
public class ProfileManagementStep extends ApiRequestBuilder {

    private static String userUuid;

    // --- Get User Profile ---

    @Given("I set up the request structure to get user profile")
    public void setupGetUserProfile(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        resetRequest();
        setRequestStructure(superAdminToken);
        response = request.get(endpoint);
    }

    @Then("I verify that the profile is displayed successfully with {int} status code")
    public void verifyProfileDisplayed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Profile data should not be null", response.jsonPath().get("data"));
        Assert.assertNotNull("First name should not be null", response.jsonPath().getString("data.firstName"));
        Assert.assertNotNull("Email should not be null", response.jsonPath().getString("data.email"));

        // Capture user UUID for avatar change
        try {
            userUuid = response.jsonPath().getString("data.uuid");
            System.out.println("Captured User UUID: " + userUuid);
        } catch (Exception e) {
            System.out.println("Could not extract user UUID: " + e.getMessage());
        }
    }

    // --- Get Profile Without Auth ---

    @Given("I set up the request structure to get profile without authentication")
    public void setupGetProfileWithoutAuth(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .log().all();
        response = request.get(endpoint);
    }

    @Then("I verify that the request fails with {int} status code")
    public void verifyRequestFails(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Change Avatar ---

    @Given("I set up the request structure to change avatar")
    public void setupChangeAvatar(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Assert.assertNotNull("User UUID must be available from profile step", userUuid);

        Map<String, String> payload = ProfilePayloadGenerator.generateChangeAvatarPayload();

        resetRequest();
        setRequestStructure(superAdminToken);
        request.body(payload);
        response = request.put(endpoint + "/" + userUuid);
    }

    @Then("I verify that the avatar is changed successfully with {int} status code")
    public void verifyAvatarChanged(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
