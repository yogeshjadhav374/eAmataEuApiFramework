package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Audit Logs API endpoints (Home Care Portal).
 */
public class HCPAuditLogsStep extends ApiRequestBuilder {

    @Given("I set up the HCP request structure to get audit logs")
    public void setupHCPGetAuditLogs(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        // ActivityController only accepts optional 'search' param — no page/size
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(new HashMap<>());
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP audit logs are returned successfully with {int} status code")
    public void verifyHCPAuditLogsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
    }
}
