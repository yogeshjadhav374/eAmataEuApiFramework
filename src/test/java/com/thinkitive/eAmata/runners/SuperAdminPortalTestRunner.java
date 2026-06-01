package com.thinkitive.eAmata.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Test runner for Super Admin Portal API automation tests.
 * Run specific features using tags: -Dcucumber.filter.tags="@Authentication"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "C:/Users/TTPL-LNVE15-0154/Desktop/eAmata-portal/tests/eAmataEuApiFramework/src/test/resources/features/Super_Admin_Portal",
        glue = {"C:/Users/TTPL-LNVE15-0154/Desktop/eAmata-portal/tests/eAmataEuApiFramework/src/test/java/com/thinkitive/eAmata/stepDefinitions"},
//        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
//                "html:target/cucumber-reports/super-admin-portal.html",
//                "junit:target/cucumber-reports/super-admin-portal.xml"},
        tags = "@getTheUsersList"
)
public class SuperAdminPortalTestRunner {
}
