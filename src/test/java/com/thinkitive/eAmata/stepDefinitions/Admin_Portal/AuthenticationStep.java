package com.thinkitive.eAmata.stepDefinitions.Admin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.AuthPayloadGenerator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.Map;

/**
 * Step definitions for Authentication API endpoints.
 */
public class AuthenticationStep extends ApiRequestBuilder {

    private String refreshToken;
    private String accessToken;

    // --- Login Steps ---

    @Given("I set up the request structure to login with valid credentials")
    public void loginWithValidCredentials(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, String> payload = AuthPayloadGenerator.generateLoginPayload(
                propertyHandler.getProperty("SuperAdminEmail"),
                propertyHandler.getProperty("SuperAdminPassword")
        );

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload).log().all();
        response = request.post(endpoint);

        // Store tokens for subsequent steps
        if (response.getStatusCode() == 200) {
            accessToken = response.jsonPath().getString("data.access_token");
            refreshToken = response.jsonPath().getString("data.refresh_token");
        }
    }

    @Then("I verify that the login is successful with {int} status code")
    public void verifyLoginSuccess(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Access token should not be null", response.jsonPath().getString("data.access_token"));
        Assert.assertNotNull("Refresh token should not be null", response.jsonPath().getString("data.refresh_token"));
        Assert.assertNotNull("Token type should not be null", response.jsonPath().getString("data.token_type"));
    }

    @Given("I set up the request structure to login with invalid credentials")
    public void loginWithInvalidCredentials(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, String> payload = AuthPayloadGenerator.generateInvalidLoginPayload();

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload).log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the login fails with {int} status code")
    public void verifyLoginFailure(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the request structure to login with blank username")
    public void loginWithBlankUsername(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, String> payload = AuthPayloadGenerator.generateLoginPayload("", "Test@123");

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload).log().all();
        response = request.post(endpoint);
    }

    // --- Logout Steps ---

    @And("I set up the request structure to logout")
    public void setupLogout(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, String> payload = AuthPayloadGenerator.generateLogoutPayload(refreshToken);

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .body(payload).log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the logout is successful with {int} status code")
    public void verifyLogoutSuccess(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Refresh Token Steps ---

    @And("I set up the request structure to refresh the access token")
    public void setupRefreshToken(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("refreshToken", refreshToken)
                .log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the token refresh is successful with {int} status code")
    public void verifyTokenRefreshSuccess(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("New access token should not be null", response.jsonPath().getString("data.access_token"));
    }

    // --- Verify User Email Steps ---

    @Given("I set up the request structure to verify user email")
    public void setupVerifyUserEmail(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String email = propertyHandler.getProperty("SuperAdminEmail");

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .log().all();
        response = request.post(endpoint + "/" + email);
    }

    @Then("I verify that the email verification response is {int} status code")
    public void verifyEmailVerificationResponse(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Change Password Steps ---

    @Given("I set up the request structure to change password with valid data")
    public void setupChangePasswordValid(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, String> payload = AuthPayloadGenerator.generateChangePasswordPayload(
                propertyHandler.getProperty("SuperAdminPassword")
        );
        // Use same password to avoid breaking the test account
        payload.put("newPassword", propertyHandler.getProperty("SuperAdminPassword"));

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify the change password response with {int} status code")
    public void verifyChangePasswordResponse(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the request structure to change password with invalid old password")
    public void setupChangePasswordInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, String> payload = AuthPayloadGenerator.generateChangePasswordPayload("WrongPassword@123");

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that change password fails with {int} status code")
    public void verifyChangePasswordFailure(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
