package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.AuditLogPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Step definitions for the Audit Logs API (Super Admin Portal).
 * <p>
 * The Audit Log controller is mounted at {@code /api/admin/audit-logs}, which is
 * OUTSIDE the framework's default {@code /api/master/} base path. Every step therefore
 * sets the base path explicitly to {@code /api/admin/audit-logs} and appends the
 * specific sub-resource (e.g. {@code /action-types}, {@code /users}, {@code /save}).
 */
public class AuditLogsStep extends ApiRequestBuilder {

    private static final String AUDIT_BASE_PATH = "/api/admin/audit-logs";

    /**
     * Prepares a fresh authenticated JSON request bound to the audit-logs base path.
     *
     * @param token the bearer token to authenticate with
     */
    private void prepareAuditRequest(String token) {
        prepareAuditRequest(token, "application/json");
    }

    /**
     * Prepares a fresh authenticated request bound to the audit-logs base path
     * with an explicit Accept media type (e.g. octet-stream for CSV export).
     *
     * @param token      the bearer token to authenticate with
     * @param acceptType the Accept header value to use
     */
    private void prepareAuditRequest(String token, String acceptType) {
        resetRequest();
        request.baseUri(propertyHandler.getProperty("baseUri"))
                .basePath(AUDIT_BASE_PATH)
                .header("Accept", acceptType)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all();
    }

    // --- Get Audit Logs (list + filters) ---

    @Given("I set up the request structure to retrieve audit logs")
    public void setupGetAuditLogs(Map<String, String> data) {
        prepareAuditRequest(superAdminToken);
        request.queryParam("page", data.getOrDefault("page", "0"))
                .queryParam("size", data.getOrDefault("size", "20"));
        if (data.containsKey("actionType")) {
            request.queryParam("actionType", data.get("actionType"));
        }
        if (data.containsKey("module")) {
            request.queryParam("module", data.get("module"));
        }
        if (data.containsKey("role")) {
            request.queryParam("role", data.get("role"));
        }
        if (data.containsKey("searchString")) {
            request.queryParam("searchString", data.get("searchString"));
        }
        if (data.containsKey("startDate") && data.containsKey("endDate")) {
            request.queryParam("startDate", data.get("startDate"))
                    .queryParam("endDate", data.get("endDate"));
        }
        response = request.get("");
    }

    @Given("I set up the request structure to retrieve audit logs for the current month")
    public void setupGetAuditLogsCurrentMonth(Map<String, String> data) {
        Instant end = Instant.now();
        Instant start = end.minus(30, ChronoUnit.DAYS);
        prepareAuditRequest(superAdminToken);
        request.queryParam("page", data.getOrDefault("page", "0"))
                .queryParam("size", data.getOrDefault("size", "20"))
                .queryParam("startDate", start.toString())
                .queryParam("endDate", end.toString());
        response = request.get("");
    }

    @Then("I verify that the audit logs are returned successfully with {int} status code")
    public void verifyAuditLogsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Audit logs data should not be null", response.jsonPath().get("data"));
        Assert.assertNotNull("Audit logs content page should not be null",
                response.jsonPath().get("data.content"));
    }

    // --- Action Types ---

    @Given("I set up the request structure to retrieve audit action types")
    public void setupGetActionTypes(Map<String, String> data) {
        prepareAuditRequest(superAdminToken);
        request.queryParam("page", data.getOrDefault("page", "0"))
                .queryParam("size", data.getOrDefault("size", "20"));
        if (data.containsKey("searchActionType")) {
            request.queryParam("searchActionType", data.get("searchActionType"));
        }
        response = request.get("/action-types");
    }

    @Then("I verify that the audit action types are returned successfully with {int} status code")
    public void verifyActionTypesReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Action types data should not be null", response.jsonPath().get("data"));
    }

    // --- Users ---

    @Given("I set up the request structure to retrieve audit log users")
    public void setupGetAuditUsers(Map<String, String> data) {
        prepareAuditRequest(superAdminToken);
        request.queryParam("page", data.getOrDefault("page", "0"))
                .queryParam("size", data.getOrDefault("size", "20"));
        if (data.containsKey("search")) {
            request.queryParam("search", data.get("search"));
        }
        if (data.containsKey("role")) {
            request.queryParam("role", data.get("role"));
        }
        response = request.get("/users");
    }

    @Then("I verify that the audit log users are returned successfully with {int} status code")
    public void verifyAuditUsersReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Users data should not be null", response.jsonPath().get("data"));
    }

    // --- Save Audit Log ---

    @Given("I set up the request structure to save a UI-initiated audit log")
    public void setupSaveAuditLog() {
        prepareAuditRequest(superAdminToken);
        request.body(AuditLogPayloadGenerator.generateSaveAuditLogPayload());
        response = request.post("/save");
    }

    @Given("I set up the request structure to save an audit log with missing required fields")
    public void setupSaveAuditLogInvalid() {
        prepareAuditRequest(superAdminToken);
        request.body(AuditLogPayloadGenerator.generateInvalidSaveAuditLogPayload());
        response = request.post("/save");
    }

    @Then("I verify that the audit log is saved successfully with {int} status code")
    public void verifyAuditLogSaved(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("I verify that saving the audit log fails with {int} status code")
    public void verifyAuditLogSaveFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- CSV Export ---

    @Given("I set up the request structure to download audit logs as CSV")
    public void setupDownloadCsv(Map<String, String> data) {
        Instant end = Instant.now();
        Instant start = end.minus(30, ChronoUnit.DAYS);
        // The /csv endpoint produces application/octet-stream, so request that media type
        // (a JSON Accept header yields HTTP 406 Not Acceptable).
        prepareAuditRequest(superAdminToken, "application/octet-stream");
        request.queryParam("startDate", data.getOrDefault("startDate", start.toString()))
                .queryParam("endDate", data.getOrDefault("endDate", end.toString()))
                .queryParam("timezone", data.getOrDefault("timezone", "UTC"));
        response = request.get("/csv");
    }

    @Then("I verify that the audit logs CSV is downloaded with {int} status code")
    public void verifyCsvDownloaded(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Authorization ---

    @Given("I set up the request structure to retrieve audit logs without a valid token")
    public void setupGetAuditLogsUnauthorized() {
        prepareAuditRequest("invalid.token.value");
        request.queryParam("page", "0").queryParam("size", "20");
        response = request.get("");
    }

    @Then("I verify that the audit logs request is rejected with {int} status code")
    public void verifyAuditLogsUnauthorized(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
