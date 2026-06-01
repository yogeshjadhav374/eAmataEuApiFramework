package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Home Care Portal Authentication API endpoints.
 */
public class HCPAuthenticationStep extends ApiRequestBuilder {

    private static String capturedRefreshToken;

    // --- Login ---

    @Given("I set up the HCP login request with valid credentials")
    public void setupHCPLoginValid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("username", propertyHandler.getProperty("HCPAdminEmail"));
        loginData.put("password", propertyHandler.getProperty("HCPAdminPassword"));

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-tenant-id", propertyHandler.getProperty("TenantId"))
                .body(loginData).log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the HCP login is successful with {int} status code")
    public void verifyHCPLoginSuccessful(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Access token should not be null", response.jsonPath().get("data.access_token"));
        try {
            capturedRefreshToken = response.jsonPath().getString("data.refresh_token");
        } catch (Exception e) {
            System.out.println("No refresh token in response: " + e.getMessage());
        }
    }

    @Given("I set up the HCP login request with invalid credentials")
    public void setupHCPLoginInvalid(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("username", "invalid@test.com");
        loginData.put("password", "WrongPassword123");

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-tenant-id", propertyHandler.getProperty("TenantId"))
                .body(loginData).log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the HCP login fails with {int} status code")
    public void verifyHCPLoginFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Refresh Token ---

    @Given("I set up the request to refresh the HCP access token")
    public void setupHCPRefreshToken(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Refresh token must be available from login step", capturedRefreshToken);

        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-tenant-id", propertyHandler.getProperty("TenantId"))
                .queryParam("refreshToken", capturedRefreshToken).log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the HCP token refresh is successful with {int} status code")
    public void verifyHCPTokenRefreshSuccessful(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Logout ---

    @Given("I set up the HCP logout request")
    public void setupHCPLogout(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + hcpAdminToken)
                .header("x-tenant-id", propertyHandler.getProperty("TenantId"))
                .log().all();
        response = request.post(endpoint);
    }

    @Then("I verify that the HCP logout is successful with {int} status code")
    public void verifyHCPLogoutSuccessful(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
