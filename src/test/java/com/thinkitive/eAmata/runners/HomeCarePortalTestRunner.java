package com.thinkitive.eAmata.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Test runner for Home Care Portal API automation tests.
 * Run specific features using tags: -Dcucumber.filter.tags="@PatientManagement"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/Home_Care_Portal",
        glue = {"com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal", "com.thinkitive.eAmata.stepDefinitions"},
        // plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        //         "html:target/cucumber-reports/home-care-portal.html",
        //         "junit:target/cucumber-reports/home-care-portal.xml"},
        tags = "@getTheUsersList"
)
public class HomeCarePortalTestRunner {
}
