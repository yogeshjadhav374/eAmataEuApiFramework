package com.thinkitive.eAmata.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = "com.thinkitive.eAmata.stepDefinitions",
        tags = "@CreateCarePlan"
)

public class TestRunner {

}

//plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
// "html:target/cucumber-reports.html"
// "junit:target/cucumber-reports/Cucumber.xml"
