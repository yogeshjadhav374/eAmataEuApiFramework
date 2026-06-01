package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Audit Logs API endpoints (Super Admin Portal).
 */
public class AuditLogsStep extends ApiRequestBuilder {

    @Given("I set up the request structure to get audit logs")
    public void setupGetAuditLogs(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        // ActivityController only supports optional 'search' param — no page/size
        ApiRequestBuilder.GetAPI(superAdminToken, new HashMap<>(), endpoint);
    }

    @Then("I verify that the audit logs are returned successfully with {int} status code")
    public void verifyAuditLogsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
    }
}
