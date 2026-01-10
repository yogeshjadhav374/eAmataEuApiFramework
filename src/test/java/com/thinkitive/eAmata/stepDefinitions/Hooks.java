package com.thinkitive.eAmata.stepDefinitions;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import utils.AuthManager;


public class Hooks extends ApiRequestBuilder {
    private static boolean isLoggedIn = false;

    @Before(order = 0)
    public static void generateTokensOnce() {

        if (!isLoggedIn) {
            // Super Admin Token
            superAdminToken =
                    AuthManager.superAdminLogin(
                            propertyHandler.getProperty("SuperAdminEmail"),
                            propertyHandler.getProperty("SuperAdminPassword")
                    );

            // HCP Admin Token (with Tenant)
            hcpAdminToken = AuthManager.hcpAdminLogin(
                    propertyHandler.getProperty("HCPAdminEmail"),
                    propertyHandler.getProperty("HCPAdminPassword")
            );

        }
        isLoggedIn = true;
    }
}

