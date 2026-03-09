package com.thinkitive.eAmata.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Test runner for Admin Portal API automation tests.
 * Run specific features using tags: -Dcucumber.filter.tags="@Authentication"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/Admin_Portal",
        glue = {"com.thinkitive.eAmata.stepDefinitions.Admin_Portal", "com.thinkitive.eAmata.stepDefinitions"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-reports/admin-portal.html",
                "junit:target/cucumber-reports/admin-portal.xml"},
        tags = "@AdminPortal and not @KnownIssue"
)
public class AdminPortalTestRunner {
}
