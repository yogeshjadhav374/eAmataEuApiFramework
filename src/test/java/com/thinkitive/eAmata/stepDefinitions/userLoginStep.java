package com.thinkitive.eAmata.stepDefinitions;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class userLoginStep extends ApiRequestBuilder {
    int actualstatusCode;
    static Map<String, Object> loginData;
    static String endPoint;

    @Given("I set up the structure to login User")
    public static String iSetUpTheStructureToLoginUser(Map<String, Object> data) {
        System.out.println("data: " + data.get("endpoint").toString());
        try {
            endPoint = data.get("endpoint").toString();
            //  System.out.println("endpoint: " + endPoint);

            loginData = new HashMap<>();
            loginData.put("username", propertyHandler.getProperty("SuperAdminEmail"));
            loginData.put("password", propertyHandler.getProperty("password"));
            // System.out.println("logindata: " + loginData);

        } catch (Exception e) {
            throw new RuntimeException("Object not found: " + loginData);
        }
            ApiRequestBuilder.loginPostRequest(null, loginData, endPoint);
        System.out.println("access_Token"+response.jsonPath().get("data.access_token"));
            SuperAdminAccessToken = response.jsonPath().get("data.access_token");
        return SuperAdminAccessToken;
    }


    @Then("I verify that the user is able to login on the super admin portal successfully")
    public void iVerifyThatTheUserIsAbleToLoginOnTheSuperAdminPortalSuccessfully() {
        response.prettyPrint();
        actualstatusCode = response.getStatusCode();
        Assert.assertEquals(200, actualstatusCode);
    }


}
