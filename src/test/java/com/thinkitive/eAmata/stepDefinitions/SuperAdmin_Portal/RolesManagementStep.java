package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Step definitions for Roles and Privileges Management API endpoints (Super Admin Portal).
 */
public class RolesManagementStep extends ApiRequestBuilder {

    private static List<Map<String, Object>> capturedRoles;

    // --- Get All Roles ---

    @Given("I set up the request structure to get all roles")
    public void setupGetAllRoles(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        ApiRequestBuilder.GetAPI(superAdminToken, new HashMap<>(), endpoint);
    }

    @Then("I verify that the roles list is returned successfully with {int} status code")
    public void verifyRolesListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            capturedRoles = response.jsonPath().getList("data");
            System.out.println("Captured " + (capturedRoles != null ? capturedRoles.size() : 0) + " roles");
        } catch (Exception e) {
            System.out.println("Could not capture roles: " + e.getMessage());
        }
    }

    // --- Update Role Privileges ---

    @Given("I set up the request structure to update role privileges")
    public void setupUpdateRolePrivileges(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        // Build List<RolePrivilegeUpdateRequest>: [{roleId, privilegeId, granted}]
        // Extract first role and first privilege from captured roles to build a valid payload
        List<Map<String, Object>> requestList = new ArrayList<>();
        if (capturedRoles != null && !capturedRoles.isEmpty()) {
            for (Map<String, Object> role : capturedRoles) {
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
                            break; // only one entry needed
                        }
                    }
                }
            }
        }
        resetRequest();
        setRequestStructure(superAdminToken);
        setRequestBody(requestList);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the role privileges are updated with {int} status code")
    public void verifyRolePrivilegesUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Reset Role Privileges ---

    @Given("I set up the request structure to reset role privileges")
    public void setupResetRolePrivileges(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        ApiRequestBuilder.PostAPI(superAdminToken, new HashMap<>(), endpoint);
    }

    @Then("I verify that the role privileges are reset with {int} status code")
    public void verifyRolePrivilegesReset(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
