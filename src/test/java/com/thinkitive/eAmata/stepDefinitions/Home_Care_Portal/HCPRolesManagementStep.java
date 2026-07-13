package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Step definitions for Roles and Privileges Management API endpoints (Home Care Portal).
 */
public class HCPRolesManagementStep extends ApiRequestBuilder {

    private static List<Map<String, Object>> capturedHCPRoles;

    @Given("I set up the HCP request structure to get all roles")
    public void setupHCPGetAllRoles(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(new HashMap<>());
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the HCP roles list is returned successfully with {int} status code")
    public void verifyHCPRolesListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            capturedHCPRoles = response.jsonPath().getList("data");
        } catch (Exception e) {
            System.out.println("Could not capture roles: " + e.getMessage());
        }
    }

    @Given("I set up the HCP request structure to update role privileges")
    public void setupHCPUpdateRolePrivileges(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        // Build List<RolePrivilegeUpdateRequest>: [{roleId, privilegeId, granted}]
        List<Map<String, Object>> requestList = new ArrayList<>();
        if (capturedHCPRoles != null && !capturedHCPRoles.isEmpty()) {
            for (Map<String, Object> role : capturedHCPRoles) {
                Object roleId = role.get("id");
                Object privileges = role.get("privileges");
                if (roleId != null && privileges instanceof List) {
                    List<?> privList = (List<?>) privileges;
                    if (!privList.isEmpty() && privList.get(0) instanceof Map) {
                        Map<?, ?> firstPriv = (Map<?, ?>) privList.get(0);
                        Object privId = firstPriv.get("id");
                        if (privId != null) {
                            Map<String, Object> updateReq = new HashMap<>();
                            updateReq.put("roleId", roleId);
                            updateReq.put("privilegeId", privId);
                            updateReq.put("granted", true);
                            requestList.add(updateReq);
                            break;
                        }
                    }
                }
            }
        }
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(requestList);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the HCP role privileges are updated with {int} status code")
    public void verifyHCPRolePrivilegesUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the HCP request structure to reset role privileges")
    public void setupHCPResetRolePrivileges(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(new HashMap<>());
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the HCP role privileges are reset with {int} status code")
    public void verifyHCPRolePrivilegesReset(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
