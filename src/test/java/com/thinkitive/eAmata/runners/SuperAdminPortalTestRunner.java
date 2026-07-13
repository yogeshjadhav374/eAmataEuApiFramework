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
        features = "src/test/resources/features/Super_Admin_Portal",
        glue = {"com.thinkitive.eAmata.stepDefinitions"},
        tags = "@getTheUsersList"
)
public class SuperAdminPortalTestRunner {
}
