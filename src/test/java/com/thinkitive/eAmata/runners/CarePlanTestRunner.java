package com.thinkitive.eAmata.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/Super_Admin_Portal/CarePlan.feature",
        glue = "com.thinkitive.eAmata.stepDefinitions",
        tags = "@CarePlan",
        plugin = {"pretty", "html:target/cucumber-reports/CarePlan.html"}
)
public class CarePlanTestRunner {
}
