package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Method;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Audit Logs API endpoints (Home Care Portal).
 * <p>
 * The audit-logs endpoint lives at {@code /api/admin/audit-logs}, which is OUTSIDE the
 * framework's default {@code /api/master/} base path, so the endpoint is supplied from the
 * feature file and set as the base path. Auth, tenant and common headers are reused from
 * {@link ApiRequestBuilder#setRequestStructure(String, String)}.
 */
public class HCPAuditLogsStep extends ApiRequestBuilder {

    @Given("I set up the HCP request structure to get audit logs")
    public void setupHCPGetAuditLogs(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "20"));

        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        // audit-logs is outside the default /api/master/ base path, so use the feature endpoint as the path
        request.basePath(endpoint);
        setQueryParams(queryParams);
        execute(Method.GET, "");
    }

    @Then("I verify that the HCP audit logs are returned successfully with {int} status code")
    public void verifyHCPAuditLogsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Audit logs data should not be null", response.jsonPath().get("data"));
        Assert.assertNotNull("Audit logs content page should not be null",
                response.jsonPath().get("data.content"));
    }
}
